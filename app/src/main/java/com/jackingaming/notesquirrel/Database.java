package com.jackingaming.notesquirrel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//SQLiteOpenHelper is an abstract class.
public class Database extends SQLiteOpenHelper {

    //@param context is the Activity that will create a data (in this case, ImageActivity).
    public Database(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    //NOT called when we run the constructor... it's called when we try to use our database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this is for creating tables within the database (which was created in the constructor).
        //we just have one table for this database.
        String sql = "create table POINTS (ID INTEGER PRIMARY KEY, X INTEGER NOT NULL, Y INTEGER NOT NULL)";

        db.execSQL(sql);
    }

    //won't need this method for creating our database (may use it in later tutorial).
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //left intentionally blank.
    }

}