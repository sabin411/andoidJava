package com.example.googlelogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlelogin.Journal.JournalRepository;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private TextInputEditText editDate;
    private AppCompatEditText location;
    private ImageView imageView;
    private AppCompatEditText title;
    private AppCompatEditText description;
    private AppCompatButton saveJournal;
    private FloatingActionButton floatingActionButton;
    private FloatingActionButton floatingActionButton2;
    private JournalRepository repository;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_PICK_CODE = 1001;
    Uri imageUri;
    FirebaseAuth fireAuth;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        editDate = findViewById(R.id.editDate);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        imageView = findViewById(R.id.imageView);

        saveJournal = findViewById(R.id.saveJournal);
        saveJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, Dashboard.class));
                uploadToFirebase();
            }
        });

        final FloatingActionButton buttontwo = findViewById(R.id.floatingActionButton2);
        buttontwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        // permission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // popup for runtime permission
                        requestPermissions(permissions, PERMISSION_PICK_CODE);
                    } else {
                        // permission granted
                        pickImageFromGallery();
                    }
                } else {
                    // system is less than marshmallow
                    pickImageFromGallery();
                }
            }
        });


        editDate = findViewById(R.id.editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //Date Picker Dialogue

                DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        editDate.setText((month + 1) + "-" + dayOfMonth + "-" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        final FloatingActionButton button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(view -> {
            checkAndRequestPermissions();
        });
    }
// this to
    private void uploadToFirebase() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving");
        progressDialog.show();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference storageReference = firebaseStorage.getReference("Photos").child("Image1" +new Random().nextInt(60));

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        progressDialog.dismiss();

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                RecViewDataHolder object = new RecViewDataHolder(
                                        editDate.getText().toString(),
                                        title.getText().toString(),
                                        description.getText().toString(),
                                        location.getText().toString(),
                                        uri.toString());


                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = firebaseDatabase.getReference("JournalEntries").child(Dashboard.uid);
                                databaseReference.push().setValue(object);


                                editDate.setText("");
                                title.setText("");
                                description.setText("");
                                location.setText("");
                                imageView.setImageResource(R.drawable.ic_launcher_background);

                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(HomeActivity.this, Dashboard.class));
                                finish();

                            }
                        });
                    }
                });
    }
// this

    private void pickImageFromGallery() {
        // intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    // handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PICK_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    pickImageFromGallery();
                } else {
                    // permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkValues(){
        String t = title.getText().toString().trim();
        String d = description.getText().toString().trim();
        if(t.isEmpty()){
            title.setHint("Title is empty *");
            title.setHintTextColor(ContextCompat.getColor(this,R.color.red));
            return false;
        }else if(d.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    int code = 102;
    void checkAndRequestPermissions(){
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions((new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}),100);
        }
        else if(code == 102){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 101);
//            launchCameraIntent.launch(cameraIntent);
        }
        else{
            Intent gallery = new Intent();
            gallery.setAction(Intent.ACTION_VIEW);
            gallery.setType("img/*");
            startActivityForResult(gallery, 102);

            Intent intent = new Intent(Intent.ACTION_PICK);
            startActivityForResult(intent, 103);
        }
    }
// set image to image view (camera and gallery)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();
        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

        }
//        else if(requestCode == 102 && resultCode == Activity.RESULT_OK){
//            Log.e("select response", data.getExtras().get("data").toString());
//        }
        else if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageView.setImageURI(data.getData());
        }

    }
}
