package com.example.googlelogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.googlelogin.Journal.Journal;
import com.example.googlelogin.Journal.JournalRepository;

public class ActivityEntryJournal extends AppCompatActivity implements View.OnClickListener, LocationListener {
    private LocationManager locationManager;
    private Button openCameraBtn;
    private ImageView imageView;
    private AppCompatTextView title;
    private AppCompatEditText description;
    private AppCompatImageButton addLocation;
    private AppCompatTextView txtLocation;
    private AppCompatButton saveJournal;
    private Location location;
    private JournalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_journal);

        repository = new JournalRepository(this);
        title = findViewById(R.id.journeyTitle);
        description = findViewById(R.id.description);
        addLocation = findViewById(R.id.addLocation);
        txtLocation = findViewById(R.id.txtLocation);
        saveJournal = findViewById(R.id.saveJournal);

        saveJournal.setOnClickListener(this);
        addLocation.setOnClickListener(this);
        accessLocationService();


        openCameraBtn = findViewById(R.id.openCameraBtn);
        imageView = findViewById(R.id.imageView);
        openCameraBtn.setOnClickListener(view -> {
    checkAndRequestPermissions();

        });
    }
    @Override
    public void onClick(View v){
        if (v.getId() == R.id.saveJournal) {
            if(checkValues()){
                long check = repository.insertJournal(
                        new Journal(
                                title.getText().toString(),
                                description.getText().toString(),
                                "",
                                "",
                                location.getLatitude(),
                                location.getLongitude(),
                                0));
                Log.e("inserted",check+"");
            }
        } else if (v.getId() == R.id.addLocation) {
            if (location != null)
                txtLocation.setText("lat: " + location.getLatitude() + ", lng: " + location.getLongitude());
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
//            Intent gallery = new Intent();
//            gallery.setAction(Intent.ACTION_VIEW);
//            gallery.setType("img/*");
//            startActivityForResult(gallery, 102);

            Intent intent = new Intent(Intent.ACTION_PICK);
            startActivityForResult(intent, 103);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        else if(requestCode == 102 && resultCode == Activity.RESULT_OK){
            Log.e("select response", data.getExtras().get("data").toString());
        }
    }

//    for location retrive
    private  void accessLocationService(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location = location;
        Log.e("current Location", location.getLatitude() + ", " + location.getLongitude());
    }
}
