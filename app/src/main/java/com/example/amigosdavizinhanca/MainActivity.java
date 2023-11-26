package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.amigosdavizinhanca.model.UserDataBase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("PrefsFile", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    public void loginButtonOnClick(View v){
        EditText loginEmailEditText = findViewById(R.id.loginEmail);
        EditText loginPasswordEditText = findViewById(R.id.loginPassword);

        String user = loginEmailEditText.getText().toString();
        String password = loginPasswordEditText.getText().toString();

        UserDataBase userDataBase = new UserDataBase(this);
        long loggedUserId = userDataBase.verifyLogin(user, password);

        if (loggedUserId != -1) {
            SharedPreferences settings = getSharedPreferences("PrefsFile", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putLong("userId", loggedUserId);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, Feed.class);
            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.incorrect_email_or_password);
            builder.setPositiveButton(R.string.ok, null);
            builder.create().show();
        }
    }

    public void redirectSignUpOnClick(View v){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }
}