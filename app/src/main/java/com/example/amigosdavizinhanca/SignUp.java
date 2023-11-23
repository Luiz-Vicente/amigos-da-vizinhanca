package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUpBottonClick(View v){
        EditText signUpNameEditText = findViewById(R.id.signUpName);
        EditText signUpCPFEditText = findViewById(R.id.signUpCPF);
        EditText signUpAddressEditText = findViewById(R.id.signUpAddress);
        EditText signUpEmailEditText = findViewById(R.id.signUpEmail);
        EditText signUpPasswordEditText = findViewById(R.id.signUpPassword);

        String name = signUpNameEditText.getText().toString();
        String cpf = signUpCPFEditText.getText().toString();
        String address = signUpAddressEditText.getText().toString();
        String email = signUpEmailEditText.getText().toString();
        String password = signUpPasswordEditText.getText().toString();


        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
    }







}