package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginButtonOnClick(View v){
        EditText loginEmailEditText = findViewById(R.id.loginEmail);
        EditText loginPasswordEditText = findViewById(R.id.loginPassword);

        String user = loginEmailEditText.getText().toString();
        String password = loginPasswordEditText.getText().toString();

        Intent intent = new Intent(MainActivity.this, Feed.class);
        startActivity(intent);
    }

    public void redirectSignUpOnClick(View v){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }
}