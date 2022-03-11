package com.example.googlelogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

public class ActivityEntryJournal extends AppCompatActivity {
    private Button openCameraBtn;
    private ImageView imageView;
    private AppCompatEditText title, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_journal);

        openCameraBtn = findViewById(R.id.openCameraBtn);
        imageView = findViewById(R.id.imageView);
        openCameraBtn.setOnClickListener(view -> {
    checkAndRequestPermissions();

        });
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
}
