package com.example.rmd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Signup extends AppCompatActivity {
    EditText name, phn, pass;
    ImageView img;
    Button next;
    private static final int REQUEST_CODE = 1;
    RadioGroup gallery;
    RadioButton radioButton;

    private Uri mImageUri;

    private StorageReference mStorageRef;


    // Create a reference to the storage location where you want to upload the image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.password2);
        phn = findViewById(R.id.phn);
        pass = findViewById(R.id.password);
        img = findViewById(R.id.imageView3);
        next = findViewById(R.id.button);
        gallery = (RadioGroup) findViewById(R.id.radio_btn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagepicker();

            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam = name.getEditableText().toString();
                String pa = pass.getEditableText().toString();
                String phone = phn.getEditableText().toString();
                String c= String.valueOf(mImageUri);
                int selectedId = gallery.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                String radio = radioButton.getText().toString();
                String domain="";
                if(radio.equals(null) || radio.equals("")){
                    Toast.makeText(Signup.this, "select your domain", Toast.LENGTH_SHORT).show();
                }
                else if(radio.equals("Pheomonia Specalists")){
                    domain="lung";
                }
                else{
                    domain="Heart";

                }
                if (phone.isEmpty() || nam.isEmpty() || pa.isEmpty()|| c.isEmpty()|| domain.equals("")) {
                    Toast.makeText(Signup.this, " Fill your details", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Signup.this, Signup2.class);
                    intent.putExtra("pass", pa);
                    intent.putExtra("phn", phone);
                    intent.putExtra("name", nam);
                    intent.putExtra("domain", domain);
                    intent.putExtra("url","https://firebasestorage.googleapis.com/v0/b/b-e-project.appspot.com/o/doc.jpg?alt=media&token=282eb83b-103e-4fc0-8b18-215cdcdf985f");
                  //  intent.putExtra("c", nam);

                    startActivity(intent);
                }


            }
        });


    }

    private void imagepicker() {
        // Registers a photo picker activity launcher in single-select mode.

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            startActivityForResult(intent, 102);
        }
        public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == 102) {
                    final FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference FS = storage.getReference("hello");
                    img.setImageURI(data.getData());
                    String strogekey = "1";
                    FS.child(strogekey).putFile(data.getData()).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            //submit.setEnabled(false);
                            Toast.makeText(Signup.this, "Please wait, Image is being uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {

                            }
                        }
                    }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            return FS.child(strogekey).getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                        }
                    });
                }


            }

        }
}
