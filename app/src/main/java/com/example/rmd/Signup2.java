package com.example.rmd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class Signup2 extends AppCompatActivity {
    SupportMapFragment supportMapFragment;
    private Uri mImageUri;
    ImageButton upload,view;
    Button b;
    Double lat=19.009773,lang=72.901756;

    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
         supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        //  client = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();
        b= findViewById(R.id.button2);
        view= findViewById(R.id.imageButton2);
        ImageButton up= findViewById(R.id.imageButton);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Signup2.this);

// Set the content view to your layout file
                dialog.setContentView(R.layout.liner);
                // Retrieve the ImageView from the layout
                ImageView imageView = dialog.findViewById(R.id.image_view);

// Load the image using Glide
                String imageUrl = "https://firebasestorage.googleapis.com/v0/b/b-e-project.appspot.com/o/bcg.png?alt=media&token=b39e7669-f5db-4c27-94ea-758443ef01ce";
                Glide.with(Signup2.this)
                        .load(imageUrl)
                        .into(imageView);
                dialog.show();

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String name = intent.getStringExtra("name");
               String phn = intent.getStringExtra("phn");
               String pass = intent.getStringExtra("pass");
               String url=intent.getStringExtra("url");
               String domain= intent.getStringExtra("domain");
               FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Address").setValue("Mumbai");
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Name").setValue(name);
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Password").setValue(pass);
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Latitude").setValue(String.valueOf(lat));
                    FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Longitude").setValue(String.valueOf(lang));
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Phone").setValue(phn);
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Address").setValue("Mumbai");
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Spl").setValue("Heart surgeon");
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("surl").setValue(url);
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Bibliography").setValue("Hello world");
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Status").setValue("pending");
                FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phn).child("Rating").setValue(5);
                Toast.makeText(Signup2.this, "Account created sucessfully, Kindly login", Toast.LENGTH_SHORT).show();
                Intent intennt = new Intent(Signup2.this, Login.class);

                //  intent.putExtra("c", nam);

                startActivity(intennt);
               // Toast.makeText(Signup2.this, "Account created sucessfully, Kindly login", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getCurrentLocation() {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap map) {
                LatLng initialPosition = new LatLng(19.009773, 72.901756);
                 Marker mMarker = map.addMarker(new MarkerOptions()
                        .position(initialPosition)
                        .draggable(true)
                        .title("Drag me!"));
                map.moveCamera(CameraUpdateFactory.newLatLng(initialPosition));
                map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        // Handle marker drag start
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        // Handle marker drag
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        // Handle marker drag end
                        LatLng newPosition = marker.getPosition();
                        lat=marker.getPosition().latitude;
                        lang=marker.getPosition().longitude;
                        Toast.makeText(Signup2.this, "Location updated successfully", Toast.LENGTH_SHORT).show();
                        // Do something with the new position (e.g. save it to a database)
                    }
                });

            }
        });
    }

}