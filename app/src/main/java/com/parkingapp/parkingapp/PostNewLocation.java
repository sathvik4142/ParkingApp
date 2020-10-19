package com.parkingapp.parkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostNewLocation extends AppCompatActivity {

    Button postButton;
    private EditText rate;
    private EditText address;
    private EditText parkingSpots;
    private EditText startTime;
    private EditText endTime;
    private EditText longitude;
    private EditText latitude;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_location);

        rate = findViewById(R.id.rate);
        address = findViewById(R.id.address);
        parkingSpots = findViewById(R.id.parkingSpots);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);

        ref = database.getReference("Locations");
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double hourlyRate = Double.parseDouble(rate.getText().toString());
                String a = address.getText().toString();
                int spots = Integer.parseInt(parkingSpots.getText().toString());
                String lower = startTime.getText().toString();
                String upper = endTime.getText().toString();
                double longi = Double.parseDouble(longitude.getText().toString());
                double lati = Double.parseDouble(latitude.getText().toString());
                ParkingSpot p = new ParkingSpot(a, lower, upper, hourlyRate, spots, longi, lati);
                String id = ref.push().getKey();
                p.id = id;
                ref.child(id).setValue(p);
                finish();
            }
        });
    }
}
