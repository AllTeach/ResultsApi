package com.example.resultsapi;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.TextUtilsCompat;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ActivitySelectFile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);
    }
    // Launcher
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {

            if(uri!=null) {
                // get the file name and type from URI
                String fileDetails = getfileDetails(uri);
                // set result in text View
                TextView textView = findViewById(R.id.tvFileName);
                textView.setText(fileDetails);
            }

            });

    private String getfileDetails(Uri uri) {
        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String details = "name: "+ returnCursor.getString(nameIndex) + "\nsize: " +returnCursor.getLong(sizeIndex);
        return details;
    }


    public void launchForResult(View view)
    {

        // Get the file type extension from the user
        EditText etFileType = findViewById(R.id.editTextFileType);
        if(!TextUtils.isEmpty(etFileType.getText())) {
            String fileExtention = etFileType.getText().toString();

            // get the Mime from the file extension
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtention.toLowerCase());

            // Pass the Mime, this will filter the results provided
            mGetContent.launch(mimeType);
        }


    }
}