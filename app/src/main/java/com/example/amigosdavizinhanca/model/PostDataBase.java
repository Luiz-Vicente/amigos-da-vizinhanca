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

    private static final String DB_NAME = "posts.sqlite";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Post";
    private static final String COL_ID = "id";
    private static final String COL_IDUSER = "idUser";
    private static final String COL_TEXT = "text";
    private static final String COL_IMAGE = "image";

    public PostDataBase(Context context ) {super(context, DB_NAME, null, DB_VERSION); }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table if not exists " + DB_TABLE + " ( " +
                COL_ID + " integer primary key autoincrement, " +
                COL_IDUSER + " int, " +
                COL_TEXT + " text, " +
                COL_IMAGE + " text) ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long createPostInDB(@NonNull Post p){
        ContentValues values = new ContentValues();
        values.put(COL_IDUSER, p.getIdUser());
        values.put(COL_TEXT, p.getText());
        values.put(COL_IMAGE, p.getImage());
        SQLiteDatabase database = getWritableDatabase();
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return id;
    }

    public long insertPostInDB (@NonNull Post p){
        ContentValues values = new ContentValues();
        values.put(COL_ID, p.getId());
        values.put(COL_IDUSER, p.getIdUser());
        values.put(COL_TEXT, p.getText());
        values.put(COL_IMAGE, p.getImage());
        SQLiteDatabase database = getWritableDatabase();
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return id;
    }

    public ArrayList<Post> getPostsFromDB() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(DB_TABLE, null, null, null, null,
                null, null, null);
        ArrayList<Post> posts = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
                long idUser = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(COL_IDUSER)));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXT));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE));
                posts.add(new Post(id, idUser, text, image));
            } while (cursor.moveToNext());
        }
    database.close();
    return posts;
    }

    public int updatePostsInDB(Post p){
        ContentValues values = new ContentValues();
        values.put(COL_IDUSER, p.getIdUser());
        values.put(COL_TEXT, p.getText());
        values.put(COL_IMAGE, p.getImage());
        String id = String.valueOf(p.getId());
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(DB_TABLE, values,
                COL_ID + "=?", new String[]{id});
        database.close();
        return  count;
    }
    public int deletePostInDB(Post p) {
        String id = String.valueOf(p.getId());
        SQLiteDatabase database = getWritableDatabase();
        int count = database.delete(DB_TABLE,
                COL_ID + "+?", new String[]{id});
        database.close();
        return count;
    }

}