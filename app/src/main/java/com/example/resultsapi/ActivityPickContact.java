package com.example.resultsapi;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ActivityPickContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);
    }
    // notice there is no Input -> Hence it's VOID
    ActivityResultLauncher<Void> mGetContact;

    {
        mGetContact = registerForActivityResult(new CustomPicker(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                    if (result != null) {
                        // get the file name and type from URI
                        String contactDetails = getContactDetails(result);
                        // set result in text View
                        TextView textView = findViewById(R.id.tvContact);
                        textView.setText(contactDetails);
                    }


        }


    });
    }

    private String getContactDetails(Uri uri) {
        String[] projection = {

                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };

        Cursor returnCursor =
                getContentResolver().query(uri, projection, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        returnCursor.moveToFirst();

        int nameColumnIndex = returnCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        String name = returnCursor.getString(nameColumnIndex);
        int hasNumber = Integer.valueOf(returnCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER));
        String number="";
        if(hasNumber>=1)
        {
            number = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        returnCursor.close();

        String retStr = "Contact Details: \nname: " + name + "\nnumber: " + number;
        return retStr;

    }

    public void launchForContact(View view) {
           mGetContact.launch(null);
    }


}