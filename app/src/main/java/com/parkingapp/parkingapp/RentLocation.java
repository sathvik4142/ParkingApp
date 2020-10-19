package com.parkingapp.parkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RentLocation extends AppCompatActivity {

    private Button confirmButton;
    private Button cancelButton;
    private TextView addressText;
    private TextView priceText;
    private TextView timeframe;
    private EditText licenseText;
    private EditText vehicleText;

    Bundle b;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_location);

        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);
        addressText = findViewById(R.id.address);
        priceText = findViewById(R.id.price);
        timeframe = findViewById(R.id.availability);
        licenseText = findViewById(R.id.license);
        vehicleText = findViewById(R.id.type);


        ref = database.getReference("Locations");


        b = this.getIntent().getExtras();

        String a = b.getString("Address");
        addressText.setText(a);
        priceText.setText("Price: " + b.getDouble("Price"));
        timeframe.setText("Available from " + b.getString("Start Time") + " to " + b.getString("End Time"));




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child(b.getString("ID")).child("inUse").setValue(true);
                ref.child(b.getString("ID")).child("currLicensePlate").setValue(licenseText.getText().toString());
                finish();
            }
        });
    }
}
