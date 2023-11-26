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

    // Criação de um banco de uma tabela em um banco sqlite
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table if not exists " + DB_TABLE + " ( " +
                COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " text, " +
                COL_CPF + " interger, " +
                COL_ADRESS + " text, " +
                COL_EMAIL + " text, " +
                COL_PASSWORD + " text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Função para criar um usuário
    public long createUserInDB (@NonNull User u){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, u.getName());
        values.put(COL_CPF, u.getCpf());
        values.put(COL_ADRESS, u.getAddress());
        values.put(COL_EMAIL, u.getEmail());
        values.put(COL_PASSWORD, u.getPassword());

        long id = -1;
        SQLiteDatabase database = getWritableDatabase();
        id = database.insert(DB_TABLE, null, values);
        database.close();

        return id;
    }

    // Função para criar uma lista de usuários
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
                long cpf = Long.parseLong(cursor.getString(
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

    // Função para autenticar o usuário
    public long verifyLogin(String email, String password) {
        SQLiteDatabase database = getReadableDatabase();

        String[] columns = {"id"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = database.query("User", columns, selection, selectionArgs, null, null, null);

        long userId = -1; // Valor padrão indicando login mal sucedido
        if (cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        }

        cursor.close();
        database.close();

        return userId;
    }

    // Função para obter um usuário com base no ID
    @Nullable
    public User getUserById(long userId) {
        SQLiteDatabase database = getReadableDatabase();

        String[] columns = {COL_ID, COL_NAME, COL_CPF, COL_ADRESS, COL_EMAIL, COL_PASSWORD};
        String selection = COL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = database.query(DB_TABLE, columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            long cpf = cursor.getLong(cursor.getColumnIndexOrThrow(COL_CPF));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(COL_ADRESS));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD));

            user = new User(id, name, cpf, address, email, password);
        }

        cursor.close();
        database.close();

        return user;
    }

    // Verifica se o e-mail já está cadastrado
    public boolean isEmailRegistered(String email) {
        SQLiteDatabase database = getReadableDatabase();

        String[] columns = {"id"};
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = database.query(DB_TABLE, columns, selection, selectionArgs, null, null, null);

        boolean isRegistered = cursor.getCount() > 0;

        cursor.close();
        database.close();

        return isRegistered;
    }

    // Verifica se o CPF já está cadastrado
    public boolean isCpfRegistered(long cpf) {
        SQLiteDatabase database = getReadableDatabase();

        String[] columns = {"id"};
        String selection = COL_CPF + " = ?";
        String[] selectionArgs = {String.valueOf(cpf)};

        Cursor cursor = database.query(DB_TABLE, columns, selection, selectionArgs, null, null, null);

        boolean isRegistered = cursor.getCount() > 0;

        cursor.close();
        database.close();

        return isRegistered;
    }
}
