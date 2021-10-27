package com.example.resultsapi;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void gotoSelectFile(View view) {
        Intent intent = new Intent(this,ActivitySelectFile.class);
        startActivity(intent);
    }

    public void gotoSelectImage(View view) {
        Intent intent = new Intent(this,ActivitySelectImage.class);
        startActivity(intent);
    }

    public void gotoPictureActivity(View view) {
        Intent intent = new Intent(this,ActivityTakePicture.class);
        startActivity(intent);
    }

    public void gotoPickContact(View view) {
        Intent intent = new Intent(this,ActivityPickContact.class);
        startActivity(intent);
    }
}