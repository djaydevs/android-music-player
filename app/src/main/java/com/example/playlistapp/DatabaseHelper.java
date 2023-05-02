package com.example.playlistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //private static final String DATABASE_NAME = "mydatabase.db";
    //private static final int DATABASE_VERSION = 1;
    //public static final String TABLE_ACCOUNTS = "tbl_accounts";

    public DatabaseHelper(Context context) {
        super(context, "mydatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_accounts(email TEXT PRIMARY KEY, password TEXT, name TEXT, contact TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_accounts");
    }

    public boolean InsertRecord(String EMAIL, String PASSWORD, String NAME, String CONTACT) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", EMAIL);
        values.put("password", PASSWORD);
        values.put("name", NAME);
        values.put("contact", CONTACT);

        long result = db.insert("tbl_accounts", null, values);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean UserLogin(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_accounts where email = ? AND password = ?", new String[]{email, password});
         if (cursor.getCount() > 0) {
             return true;
         }
         else {
             return false;
         }
    }
//    public Cursor getData(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res;
//
//        if (id == "0") {
//            res = db.rawQuery(" SELECT * FROM " + tbl_name, null);
//        } else {
//            res = db.rawQuery(" SELECT * FROM " + tbl_name + " WHERE accNum = '" + id + "'", null);
//        }
//
//        return res;
//    }

//    public boolean deleteData (String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(tbl_name, "accNum=?", new String[]{id});
//        return true;
//    }
}
