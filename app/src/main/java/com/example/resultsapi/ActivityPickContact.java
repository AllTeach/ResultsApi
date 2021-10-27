package com.example.resultsapi;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.TextView;

public class ActivityPickContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);
    }
    // notice there is no Input -> Hence it's VOID
    ActivityResultLauncher<Void> mGetContact = registerForActivityResult(new ActivityResultContracts.PickContact(),
            uri -> {

                if(uri!=null) {
                    // get the file name and type from URI
                    String contactDetails = getContactDetails(uri);
                    // set result in text View
                    TextView textView = findViewById(R.id.tvContact);
                    textView.setText(contactDetails);
                }


            });

    private String getContactDetails(Uri uri) {
        String[] projection = {

                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        };

        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        returnCursor.moveToFirst();
        //get contact details
        String contactId = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts._ID));
        String contactName = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        String idResults = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        int idResultHold = Integer.parseInt(idResults);



        int nameColumnIndex = returnCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        String name = returnCursor.getString(nameColumnIndex);

        returnCursor.close();

        String retStr = "Contact Details: \nname: " + name + "\nnumber: " ;
        return retStr;

    }

    public void launchForContact(View view) {


            mGetContact.launch(null);




    }
}