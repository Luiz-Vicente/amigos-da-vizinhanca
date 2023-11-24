package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
    }


    public void newPost(View V) {





        Intent intent = new Intent(Feed.this, CreatePost.class);
        startActivity(intent);


    }
}