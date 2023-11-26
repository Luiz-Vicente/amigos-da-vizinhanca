package com.example.amigosdavizinhanca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.amigosdavizinhanca.controller.Post;
import com.example.amigosdavizinhanca.controller.PostAdapter;
import com.example.amigosdavizinhanca.model.PostDataBase;

import java.util.List;

public class Feed extends AppCompatActivity {

    private static final int CREATE_POST_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_PERMISSION = 123;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerView = findViewById(R.id.feedList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Verificar e solicitar permissões se necessário
        checkAndRequestPermission();
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            loadPosts();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadPosts();
            } else {
                Toast.makeText(this, R.string.we_need_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadPosts() {
        PostDataBase postDataBase = new PostDataBase(this);
        List<Post> itemList = postDataBase.getPostsFromDB();

        postAdapter = new PostAdapter(itemList);
        recyclerView.setAdapter(postAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_POST_REQUEST_CODE && resultCode == RESULT_OK) {
            checkAndRequestPermission();
        }
    }

    public void newPost(View V) {
        Intent intent = new Intent(Feed.this, CreatePost.class);
        startActivityForResult(intent, CREATE_POST_REQUEST_CODE);
    }
}
