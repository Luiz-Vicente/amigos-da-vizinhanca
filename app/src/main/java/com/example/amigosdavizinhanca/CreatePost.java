package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreatePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
    }

    public void publishPost(View V) {

        EditText postTextEditText = findViewById(R.id.postText);



        Intent intent = new Intent(CreatePost.this, Feed.class);
        startActivity(intent);


    }
}