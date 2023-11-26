package com.example.amigosdavizinhanca.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.amigosdavizinhanca.controller.Post;

import java.util.ArrayList;

public class PostDataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "posts.sqlite";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Post";
    private static final String COL_ID = "id";
    private static final String COL_TEXT = "text";
    private static final String COL_CREATOR_ID = "creator_id";
    private static final String COL_URI = "uri";
    private static final String COL_DATE = "date";

    public PostDataBase(Context context ) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    // Criação de uma tabela em um banco sqlite
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS " + DB_TABLE + " ( " +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEXT + " TEXT, " +
                COL_CREATOR_ID + " INTEGER, " +
                COL_URI + " TEXT, " +
                COL_DATE + " INTERGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Função para criar um post
    public long createPostInDB(@NonNull Post post){
        ContentValues values = new ContentValues();
        values.put(COL_TEXT, post.getText());
        values.put(COL_CREATOR_ID, post.getCreatorId());
        values.put(COL_URI, post.getUri());
        values.put(COL_DATE, post.getDate());

        SQLiteDatabase database = getWritableDatabase();

        long id = database.insert(DB_TABLE, null, values);
        database.close();

        return id;
    }

    // Função para obter uma lista de Posts
    public ArrayList<Post> getPostsFromDB() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(DB_TABLE, null, null, null, null,
                null, null, null);

        ArrayList<Post> posts = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXT));
                long creatorId = cursor.getLong(cursor.getColumnIndexOrThrow(COL_CREATOR_ID));
                String uri = cursor.getString(cursor.getColumnIndexOrThrow(COL_URI));
                long date = cursor.getLong(cursor.getColumnIndexOrThrow(COL_DATE));
                posts.add(new Post(text, creatorId, uri, date));
            } while (cursor.moveToNext());
        }

        database.close();

        return posts;
    }
}