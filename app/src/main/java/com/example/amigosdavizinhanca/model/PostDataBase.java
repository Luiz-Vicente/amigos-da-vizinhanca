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
    private static final String COL_TEXT = "text";

    public PostDataBase(Context context ) {super(context, DB_NAME, null, DB_VERSION); }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table if not exists " + DB_TABLE + " ( " +
                COL_ID + " integer primary key autoincrement, " +
                COL_TEXT + " text) ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long createPostInDB(@NonNull Post p){
        ContentValues values = new ContentValues();
        values.put(COL_TEXT, p.getText());
        SQLiteDatabase database = getWritableDatabase();
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return id;
    }

    public long insertPostInDB (@NonNull Post p){
        ContentValues values = new ContentValues();
        values.put(COL_ID, p.getId());
        values.put(COL_TEXT, p.getText());
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
                String text = cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXT));
                posts.add(new Post(id, text));
            } while (cursor.moveToNext());
        }
    database.close();
    return posts;
    }

    public int updatePostsInDB(Post p){
        ContentValues values = new ContentValues();
        values.put(COL_TEXT, p.getText());
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