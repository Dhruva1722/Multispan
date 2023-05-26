package com.example.multispan.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "LoginData",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table LoginDetails(userID TEXT primary key,name TEXT ,password PASSWORD )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists LoginDetails");
    }
    public boolean insetUserData(String name ,String email,String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userID",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        long result = DB.insert("LoginDetails" , null,contentValues);
        if (result == -1){
            return  false;
        }else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from LoginDetails",null);
        return  cursor;
    }
}
