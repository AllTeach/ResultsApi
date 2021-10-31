package com.example.resultsapi;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ActivityTakePicture extends AppCompatActivity {


    private ImageView ivFull,ivThumb;
    // reigsterfor TakePicture -> this means full size image
    private ActivityResultLauncher<Uri> mGetFullPicture;
    private ActivityResultLauncher<Void> mGetThumb;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_take_picture);


        initViews();
        registerLaunchers();
    }
    private void registerLaunchers() {

        mGetThumb = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                ivThumb.setImageBitmap(result);
            }
        });
        mGetFullPicture = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                setPic();
            }
        });
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result != null) {
                    boolean read = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                    boolean write = result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (read && write)
                        ActivityTakePicture.this.takePicture();
                    else
                        Toast.makeText(ActivityTakePicture.this, "No permissions", Toast.LENGTH_LONG).show();

                }


            }

        });
    }




    private void initViews() {
        ivFull = findViewById(R.id.ivFullSize);
        ivThumb = findViewById(R.id.ivThumbnail);

    }


    // here we do the same  using the ready made by Google
    // TakePicture contract.
    // MediaStore.ACTION_IMAGE_CAPTURE take a picture} saving it into the provided
    //     content Uri}.
    //     Returns {@code true} if the image was saved into the given {@link Uri}.
    // Notice on Input/Output here:
    // it expects a URI for a file, so can work only
    // on returning the full size image and not just the thumbnail
    // when using our custom contract.
    // When using our custom contract we can do it the same way
    // or just receive the thumbnail
    // please take same picture using both and notice resolution difference!

    public void takeFullSizeImage(View view) {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED)
            takePicture();
        else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissionLauncher.launch(permissions);
        }


    // check for permission
    // only here since it might not be required
    //     if(ContextCompat.checkSelfPermission(this, Manifest.permission.EXTER))
    // To unsredtand the whole process please visit:
    // https://developer.android.com/training/camera/photobasics


    }
    public  void takePicture()
    {
        File photoFile = null;
        photoFile = createImageFile();

        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.resultsapi.fileprovider",
                    photoFile);
            mGetFullPicture.launch(photoURI);

        }

    }


    // Use the simple picture preview receiving a thumbnail
    // same as using the
    // MediaStore.ACTION_IMAGE_CAPTURE take small a picture} preview, returning it as a
    public void takeStandardThumbnail(View view) {
        mGetThumb.launch(null);


    }



    private File createImageFile()
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        File image2 = null;

       try {
            image = new File(storageDir,imageFileName + ".jpg");
            /*
            image2 = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
*/
        } catch (Exception e) {
            e.printStackTrace();
       }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void setPic() {
        ImageView imageView = findViewById(R.id.ivFullSize);

        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

}