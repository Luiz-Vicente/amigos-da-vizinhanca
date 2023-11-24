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
    ImageButton imageButton;

    int index;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        postEditText = findViewById(R.id.postText);
        imageButton = findViewById(R.id.imageButton);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            index = extra.getInt("index");
        }
        if(index != -1){
            Post p = DataModel.getInstance().getPost(index);
            postEditText.setText(p.getText());
            loadImage(p.getImage(), imageButton);
        }
    }

    private void loadImage(String imageUri, ImageButton imageButton) {
        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(this)
                    .load(imageUri)
                    .into(imageButton);
        }
    }

    public void publishPost(View V) {
        String text = postEditText.getText().toString();

        if(text.length() > 1) {
            if (index == -1){
                if (selectedImageUri != null){
                    DataModel.getInstance().addPost(
                            new Post(-1, -1 , text, selectedImageUri.toString())
                    );
                    selectedImageUri = null;
                }
            }else {
                Post p = DataModel.getInstance().getPost(index);
                p.setText(text);
                if (selectedImageUri != null) {
                    p.setImage(selectedImageUri.toString());
                    selectedImageUri = null;
                }

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
    public void chooseImage(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolher Origem da Imagem");
        builder.setItems(new CharSequence[]{"Galeria", "Câmera"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                        break;
                    case 1:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                        }
                        break;
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);

        if (resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imageButton.setImageBitmap(bitmap);;
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && data != null && data.getExtras() != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }
}