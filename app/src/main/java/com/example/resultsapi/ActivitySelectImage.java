package com.example.resultsapi;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ActivitySelectImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
    }

    // Launcher
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {

                // just an example of extracting an image
                // Handle the returned Uri
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


    public void launchForResult(View view)
    {
        // Pass the Mime as Image
        // // this will filter the results provided only images
        mGetContent.launch("image/*");

    }
}