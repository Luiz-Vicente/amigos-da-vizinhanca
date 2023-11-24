package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amigosdavizinhanca.model.UserDataBase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginButtonOnClick(View v){
        EditText loginEmailEditText = findViewById(R.id.loginEmail);
        EditText loginPasswordEditText = findViewById(R.id.loginPassword);
        TextView teste = findViewById(R.id.loginTitle);

        String user = loginEmailEditText.getText().toString();
        String password = loginPasswordEditText.getText().toString();

        boolean loginSuccessful = UserDataBase.verifyLogin(user, password, this);

        System.out.println(loginSuccessful);

        if(loginSuccessful){
            teste.setText("sim");
            Intent intent = new Intent(MainActivity.this, Feed.class);
            startActivity(intent);
        } else {
            teste.setText("Não foi possível fazer login");
        }
    }

    public void redirectSignUpOnClick(View v){
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
    }
}