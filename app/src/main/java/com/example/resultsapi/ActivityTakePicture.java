package com.example.resultsapi;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityTakePicture extends AppCompatActivity {


    private ImageView ivFull,ivThumb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        initViews();
    }

    private void initViews() {
        ivFull = findViewById(R.id.ivFullSize);
        ivThumb = findViewById(R.id.ivThumbnail);

    }

    ActivityResultLauncher<Void> mGetThumb =registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
        @Override
        public void onActivityResult(Bitmap result) {
            ivThumb.setImageBitmap(result);
        }
    });


    public void takeFullSizeImage(View view) {
    }

    public void takeStandardThumbnail(View view) {
        mGetThumb.launch(null);


    }
}