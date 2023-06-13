package com.example.rmd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
 EditText phn,pass;
    RadioGroup gallery;
    RadioButton radioButton;
    String domain;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phn= findViewById(R.id.phn);
        reg= findViewById(R.id.neww);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        pass= findViewById(R.id.password);
        Button sub= findViewById(R.id.textView5);
        gallery = (RadioGroup) findViewById(R.id.radio_btn);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = gallery.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                String radio = radioButton.getText().toString();
                if(radio.equals(null)){
                    Toast.makeText(Login.this, "select your domain", Toast.LENGTH_SHORT).show();
                }
                else if(radio.equals("Pheomonia Specalists")){
                   domain="lung";
                }
                else{
                    domain="Heart";

                }

                String phno = phn.getEditableText().toString();
                String passw = pass.getEditableText().toString();
                if (phno.isEmpty() || passw.isEmpty()) {
                    Toast.makeText(Login.this, "Enter valid credintails", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
                    reference.child(domain).child(phno).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String pas= snapshot.child("Password").getValue(String.class);
                                String nam= snapshot.child("Name").getValue(String.class);
                                String status= snapshot.child("Status").getValue(String.class);
                                String proflie= snapshot.child("Surl").getValue(String.class);
                                //String pas= snapshot.child("Password").getValue(String.class);
                                if(pas.equals(passw)){
                                    DatabaseReference d= FirebaseDatabase.getInstance().getReference("Doctors").child(domain).child(phno);
                                    d.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String status= snapshot.child("Status").getValue(String.class);
                                            if(status.equals("pending")){
                                                AlertDialog.Builder builder = new AlertDialog.Builder((Login.this));
                                                builder.setMessage("Verification Pending, Comeback after sometime")
                                                        .setCancelable(false)
                                                        .setPositiveButton("Back to login", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // Do something when OK button is clicked

                                                            }
                                                        });; // This line makes the dialog non-cancelable);
                                                AlertDialog alert = builder.create();
                                                alert.show();
                                            }
                                            else{
                                                Toast.makeText(Login.this, "Sucess", Toast.LENGTH_SHORT).show();
                                                Intent intent= new Intent(Login.this, main.class);
                                                intent.putExtra("phn", phno);
                                                intent.putExtra("Dom",domain);
                                                intent.putExtra("sat",status);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                }
                                else{
                                    Toast.makeText(Login.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this, "Account not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
    }
}