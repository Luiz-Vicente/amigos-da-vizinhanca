package com.example.amigosdavizinhanca;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.amigosdavizinhanca.controller.Post;
import com.example.amigosdavizinhanca.model.PostDataBase;

public class CreatePost extends AppCompatActivity {
    EditText postEditText;
    int index;
    private final int GALLERY_REQ_CODE = 2;
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(R.string.attention);
        builder.setMessage(R.string.you_really_want_to_leave);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.create().show();
    }

    public void imageButtonOnClick(View v){
        Intent iGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        iGallery.setType("image/*");
        startActivityForResult(iGallery, GALLERY_REQ_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == GALLERY_REQ_CODE){
                ImageButton btn = findViewById(R.id.imageButton);
                btn.setImageURI(data.getData());
                this.imageUri = data.getData();
            }
        }
    }

    public void publishPost(View V) {
        EditText postEditText = findViewById(R.id.postText);
        String text = postEditText.getText().toString();
        SharedPreferences settings = getSharedPreferences("PrefsFile", 0);

        long creatorId = settings.getLong("userId", -1);
        long currentTimestamp = System.currentTimeMillis();
        String uri = (this.imageUri != null) ? String.valueOf(this.imageUri) : "";

        if(text.length() > 1) {
            Post newPost = new Post(text, creatorId, uri, currentTimestamp);

            PostDataBase postDataBase = new PostDataBase(this);
            long postId = postDataBase.createPostInDB(newPost);

            if(postId != -1){
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePost.this);
                builder.setTitle(R.string.attention);
                builder.setMessage(R.string.no_published);
                builder.setPositiveButton(R.string.ok, null);
                builder.create().show();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreatePost.this);
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.you_cant_create_a_publication_without_text);
            builder.setPositiveButton(R.string.ok, null);
            builder.create().show();
        }
    }
}