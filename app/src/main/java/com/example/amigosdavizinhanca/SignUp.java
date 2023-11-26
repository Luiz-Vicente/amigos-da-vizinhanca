package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.amigosdavizinhanca.model.UserDataBase;
import com.example.amigosdavizinhanca.controller.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public boolean isValidCPF(String cpf) {
        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os caracteres são números
        if (!cpf.matches("\\d+")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cpf.charAt(9)) != firstDigit) {
            return false;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }

        // Verifica o segundo dígito verificador
        return Character.getNumericValue(cpf.charAt(10)) == secondDigit;
    }

    public void signUpButtonClick(View v){
        UserDataBase userDataBase = new UserDataBase(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.ok, null);

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

        // Validação de nome
        if(name.length()<3){
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.the_name_needs_to_have_at_less_3_letras);
            builder.create().show();

            return;
        }

        // Validação de CPF
        if (cpf.isEmpty()) {
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.fill_the_cpf_correctly);
            builder.create().show();

            return;
        }

        if(userDataBase.isCpfRegistered(Long.parseLong(cpf))){
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.this_cpf_is_already_registered);
            builder.create().show();

            return;
        }

        if(!isValidCPF(cpf)){
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.fill_the_cpf_correctly);
            builder.create().show();

            return;
        }

        // Validação de endereço
        if(address.length()<5){
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.the_address_is_too_short);
            builder.create().show();

            return;
        }



        // Validações de E-mail
        if (email.isEmpty()) {
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.the_email_is_not_valid);
            builder.create().show();

            return;
        }

        if(userDataBase.isEmailRegistered(email)){
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.this_email_is_already_registered);
            builder.create().show();

            return;
        }

        String emailRegex = "^[A-Za-z0-9_+&*-]+(?:\\.[A-Za-z0-9_+&*-]+)*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches()) {
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.the_email_is_not_valid);
            builder.create().show();

            return;
        }

        // Validação de senha
        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*()-+])[A-Za-z0-9!@#$%^&*()-+]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        if (!passwordMatcher.matches()) {
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.password_validation_rules);
            builder.create().show();
            return;
        }

        User newUser = new User(name, Long.parseLong(cpf), address, email, password);
        long userId = userDataBase.createUserInDB(newUser);
        userDataBase.close();

        if(userId != -1){
            Intent intent = new Intent(SignUp.this, MainActivity.class);
            startActivity(intent);
        } else {
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.registration_failed);
            builder.create().show();
        }
    }

    public void redirectLoginOnClick(View v){
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
    }
}