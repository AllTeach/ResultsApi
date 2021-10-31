package com.example.resultsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    public void returnData(View view) {
        Intent intent = getIntent();

        EditText name = findViewById(R.id.etTextName);//
        EditText password = findViewById(R.id.etPassword);

        intent.putExtra("name",name.getText().toString());
        intent.putExtra("password",password.getText().toString());
        setResult(Activity.RESULT_OK,intent);
        finish();


    }
}