package com.example.amigosdavizinhanca.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.amigosdavizinhanca.controller.User;

import java.util.ArrayList;

public class UserDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "users.sqlite";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "User";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_CPF = "cpf";
    private static final String COL_ADRESS = "address";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";

    public UserDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table if not exists " + DB_TABLE + " ( " +
                COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " text, " +
                COL_CPF + " int, " +
                COL_ADRESS + " text, " +
                COL_EMAIL + " text, " +
                COL_PASSWORD + " text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long postUserInDB (@NonNull User u){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, u.getName());
        values.put(COL_CPF, u.getCpf());
        values.put(COL_ADRESS, u.getAddress());
        values.put(COL_EMAIL, u.getEmail());
        values.put(COL_PASSWORD, u.getPassword());
        SQLiteDatabase database = getWritableDatabase();
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return id;
    }

    public ArrayList<User> getUsersFromDB(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(DB_TABLE, null, null, null,
                null, null, null);
        ArrayList<User> users = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do{
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_NAME));
                int cpf = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_CPF)));
                String address = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_ADRESS));
                String email = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_EMAIL));
                String password = cursor.getString(
                        cursor.getColumnIndexOrThrow(COL_PASSWORD));
                users.add(new User(id, name, cpf, address, email, password));

            } while (cursor.moveToNext());
        }

        database.close();
        return users;
    }

    public int updateUserInDB(User u){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, u.getName());
        values.put(COL_CPF, u.getCpf());
        values.put(COL_ADRESS, u.getAddress());
        values.put(COL_EMAIL, u.getEmail());
        values.put(COL_PASSWORD, u.getPassword());
        String id = String.valueOf(u.getId());
        SQLiteDatabase database = getWritableDatabase();
        int count = database.update(DB_TABLE,values,
                COL_ID + "=?", new String[]{id});
        database.close();
        return count;
    }

    public int deleteUserInDB(User u) {
        String id = String.valueOf(u.getId());
        SQLiteDatabase database = getWritableDatabase();
        int count = database.delete(DB_TABLE,
                COL_ID + "=?", new String[]{id});
        database.close();
        return count;
    }

    public static boolean verifyLogin(String email, String password, Context context) {
        UserDataBase userDataBase = new UserDataBase(context);
        SQLiteDatabase database = userDataBase.getReadableDatabase();

        Cursor cursor = database.query(
                "User",
                new String[]{"id"},
                "email = ? AND password = ?",
                new String[]{email, password},
                null,
                null,
                null
        );
        boolean loginSuccessful = cursor.moveToFirst();
        cursor.close();
        database.close();

        return loginSuccessful;
    }
}
