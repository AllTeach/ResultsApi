package com.example.resultsapi;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomPicker<Void,Uri> extends ActivityResultContract<Void,Uri> {


    @NonNull
    @Override
    public android.content.Intent createIntent(@NonNull Context context, Void v) {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        return i;
    }

    @Override
    public Uri parseResult(int resultCode, @Nullable Intent intent) {
        return (Uri) intent.getData();
    }


}
