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
import android.widget.ImageView;
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
     //   String contactId = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts._ID));
   //     String contactName = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

    //    String idResults = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
    //    int idResultHold = Integer.parseInt(idResults);

        String contactId = returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts._ID));

        int nameColumnIndex = returnCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        String name = returnCursor.getString(nameColumnIndex);

        String contactThumnail =returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
        //returnCursor.getString(returnCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));

        int hasNumber = Integer.valueOf(returnCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER));
        String number="";
        //before setting image, check if have or not
        ImageView imageView = findViewById(R.id.ivContact);
        if (contactThumnail != null){
            imageView.setImageURI(Uri.parse(contactThumnail));
        }
        else {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
        returnCursor.close();




        // display first number only
      //  String number="";
       // if(hasNumber > 0) {
         //   {
           /*     Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+contactId,
                        null,
                        null
                );
                while (cursor2.moveToNext()){
                    //get phone number
                    String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //set details

                    number+= "\nPhone: "+contactNumber;
                    //before setting image, check if have or not
                    ImageView imageView = findViewById(R.id.ivContact);

                    if (contactThumnail != null){
                        imageView.setImageURI(Uri.parse(contactThumnail));
                    }
                    else {
                        imageView.setImageResource(R.drawable.ic_launcher_background);
                    }
                }
                cursor2.close();




            }
*/




        String retStr = "Contact Details: \nname: " + name + "\nnumber: " + number;
        return retStr;

    }

    public void launchForContact(View view) {


            mGetContact.launch(null);




    }
}