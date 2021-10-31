package com.example.resultsapi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GenericResultActivity extends AppCompatActivity {

    private ActivityResultLauncher mResult = registerForActivityResult(new StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();

                String name = data.getStringExtra("name");
                String password = data.getStringExtra("password");
                Toast.makeText(GenericResultActivity.this, "result: " + name + "," + password, Toast.LENGTH_LONG).show();

            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_result);
    }

    public void gotoResultActivity(View view)
    {
        Intent i = new Intent(this,ResultActivity.class);
        mResult.launch(i);

    }
}