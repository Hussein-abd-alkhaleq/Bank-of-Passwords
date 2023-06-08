package com.example.bankofpasswords;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;



public class DataBase {
    SQLiteDatabase database;
    Context context;

    public DataBase(Context context_var) {
        context = context_var;
        DataBase_helper helper = new DataBase_helper(context);
        database = helper.getWritableDatabase();
    }

    public void insert(String name, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("password_name", name);
        contentValues.put("password_value", value);
        database.insert("passwords_table", null, contentValues);
        database.close();
    }

    public void close() {
        database.close();
    }

    public boolean same_name_exist(String password_name) {
        Cursor cursor = database.rawQuery("SELECT password_name FROM passwords_table WHERE password_name= '" + password_name + "'", null);
        return cursor.moveToFirst();
    }


    public List<List_Item> get_all_stored_passwords() {
        List<List_Item> list = new ArrayList<>();

                Cursor cursor = database.rawQuery("SELECT * FROM passwords_table ", null);
                if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {

                            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("password_name"));
                            @SuppressLint("Range") String encrypted_password_string = cursor.getString(cursor.getColumnIndex("password_value"));
                            list.add(new List_Item(name, new crypto().decrypt_with_AES(context,encrypted_password_string)));
                            cursor.moveToNext();
                        }
                        cursor.close();
                }

        return list;
    }
    public void delete_password(String password_name){
        database.delete("passwords_table","password_name=?", new String[]{password_name});
        database.close();
    }

    public void update_password(String old_password_name,String new_password_name, String new_password_value) {
        ContentValues cv = new ContentValues();
        cv.put("password_name", new_password_name);
        cv.put("password_value", new_password_value);
        database.update("passwords_table", cv, "password_name=?", new String[]{old_password_name});
        database.close();
    }

    public void reset(){
        database.execSQL("DROP TABLE IF EXISTS passwords_table ");
        database.close();

    }
}
