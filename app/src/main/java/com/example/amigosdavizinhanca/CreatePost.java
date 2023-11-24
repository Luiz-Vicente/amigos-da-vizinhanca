package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.bumptech.glide.Glide;
import com.example.amigosdavizinhanca.controller.Post;
import com.example.amigosdavizinhanca.model.DataModel;

import java.io.IOException;

public class CreatePost extends AppCompatActivity {
    EditText postEditText;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        postEditText = findViewById(R.id.postText);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            index = extra.getInt("index");
        }
        if(index != -1){
            Post p = DataModel.getInstance().getPost(index);
            postEditText.setText(p.getText());
        }
    }

    public void publishPost(View V) {
        String text = postEditText.getText().toString();

        if(text.length() > 1) {
            Post p = DataModel.getInstance().getPost(index);
            p.setText(text);

            if(index!=-1) {
                DataModel.getInstance().updatePost(p, index);
            }
            finish();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(CreatePost.this);
            builder.setTitle("Atenção");
            builder.setMessage("O post não foi salvo. Você realmente deseja sair?");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i)
                    {finish();
                }
            });
            builder.setNegativeButton(android.R.string.no, null);
            builder.create().show();
        }
    }

}