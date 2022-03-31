package com.example.googlelogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import android.os.Environment;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 101;
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
    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    DatabaseReference reference;
    String currentPhotoPath;

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
        FirebaseUser rUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = rUser.getUid();

        final StorageReference storageReference = firebaseStorage.getReference("Photos").child("Image1" +new Random().nextInt(60));

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        progressDialog.dismiss();

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                reference = FirebaseDatabase.getInstance().getReference("JournalEntries").child(userId);
                                String entryKey = reference.push().getKey();

                                RecViewDataHolder object = new RecViewDataHolder(
                                        editDate.getText().toString(),
                                        title.getText().toString(),
                                        description.getText().toString(),
                                        location.getText().toString(),
                                        uri.toString(),
                                        userId,
                                        entryKey
                                        );
                                reference.child(entryKey).setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Journal Saved successfully!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(HomeActivity.this, Dashboard.class));
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Failed to add journal:(", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "An error occurred while adding journal:(", Toast.LENGTH_SHORT).show();
                                    }
                                });
//babe ko yo chai hai paxi gayera milena vnai chai un comment garna parxa
//                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                                DatabaseReference databaseReference = firebaseDatabase.getReference("JournalEntries").child(Dashboard.uid);
//                                databaseReference.push().setValue(object);
// yaa samma
//                                editDate.setText("");
//                                title.setText("");
//                                description.setText("");
//                                location.setText("");
//                                imageView.setImageResource(R.drawable.ic_launcher_background);

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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    int code = 102;
    void checkAndRequestPermissions(){
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions((new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}),100);
        }
        else if(code == 102){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File imagefile = null;
            try{
                imagefile = createImageFile();
            }
            catch(IOException ex) {
                    Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
            }
            if(imagefile != null){
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.googlelogin.android.fileprovider",
                        imagefile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
//            startActivityForResult(cameraIntent, 101);
////            launchCameraIntent.launch(cameraIntent);
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

        if(requestCode == CAMERA_REQUEST_CODE){
            Toast.makeText(this, "hope its working", Toast.LENGTH_SHORT).show();
            File f = new File(currentPhotoPath);
            imageView.setImageURI(Uri.fromFile(f));
            Log.d("tag", "Absolute url of image is " + Uri.fromFile(f));
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
            imageUri = Uri.fromFile(f);
            return;
//            uploadImageToFirebase(f.getName(),contentUri);
        }

//        imageUri = data.getData();
//        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//
//        }
//        else if(requestCode == 102 && resultCode == Activity.RESULT_OK){
//            Log.e("select response", data.getExtras().get("data").toString());
//        }
         if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
                     imageUri = data.getData();

             imageView.setImageURI(data.getData());
            return;
        }

    }
}
