package com.parkingapp.parkingapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button newButton;
    private Button rentButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    ArrayList<ParkingSpot> spots = new ArrayList<ParkingSpot>();

    Map<String, ParkingSpot> spotMap = new HashMap<String, ParkingSpot>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        newButton = findViewById(R.id.newButton);
        rentButton = findViewById(R.id.rentButton);
        rentButton.setVisibility(View.GONE);

        ref = database.getReference("Locations");

        spots = new ArrayList<ParkingSpot>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                spots.clear();
                rentButton.setVisibility(View.GONE);
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ParkingSpot s = noteDataSnapshot.getValue(ParkingSpot.class);
                    spots.add(s);
                }
                System.out.println("Spots:" + spots);

                LatLng currentLoc = new LatLng(37.7749, -122.4194);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(currentLoc).title("Current Location"));
                spotMap.clear();

                for (ParkingSpot ps : spots)
                {
                    spotMap.put(ps.address, ps);

                    double x = ps.longitude;
                    double y = ps.latitude;

                    System.out.println("LatLng" + x + y);
                    LatLng loc = new LatLng(x, y);
                    String title = ps.address + " (";
                    if (ps.inUse)
                        title += "Not Available)";
                    else
                        title += "Available)";
                    mMap.addMarker(new MarkerOptions().position(loc).title(title));
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        marker.showInfoWindow();

                        rentButton.setVisibility(View.VISIBLE);

                        rentButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle b = new Bundle();
                                System.out.println(spotMap);
                                System.out.println("Rajesh " + marker.getTitle());
                                String ad = marker.getTitle().substring(0, marker.getTitle().indexOf("(") - 1);
                                ParkingSpot currSpot = spotMap.get(ad);

                                b.putString("Address", ad);

                                b.putString("Start Time", currSpot.lowerHour);
                                b.putString("End Time", currSpot.upperHour);
                                b.putDouble("Price", currSpot.rate);
                                b.putString("ID", currSpot.id);
                                Intent i = new Intent(MapsActivity.this, RentLocation.class);
                                i.putExtras(b);
                                startActivity(i);
                            }
                        });

                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("database failed");
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapsActivity.this, PostNewLocation.class);
                startActivity(i);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng currentLoc = new LatLng(37.7749, -122.4194);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 14.0f));

        mMap.addMarker(new MarkerOptions().position(currentLoc).title("Current Location"));
    }
}
