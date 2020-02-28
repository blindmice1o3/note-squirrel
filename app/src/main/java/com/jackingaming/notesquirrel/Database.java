package com.jackingaming.notesquirrel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

import androidx.annotation.Nullable;

import java.util.List;

//SQLiteOpenHelper is an abstract class.
public class Database extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "points";
    private static final String COL_ID = "id";
    private static final String COL_X = "x";
    private static final String COL_Y = "y";

    //@param context is the Activity that will create a data (in this case, ImageActivity).
    public Database(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    //NOT called when we run the constructor... it's called when we try to use our database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //this is for creating tables within the database (which was created in the constructor).
        //we just have one table for this database.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s INTEGER NOT NULL)",
                TABLE_NAME, COL_ID, COL_X, COL_Y);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //execute a SQL command (in this case, create a table named POINTS with 3 fields [columns]).
        ////////////////
        db.execSQL(sql);
        ////////////////
    }

    //won't need this method for creating our database (may use it in later tutorial).
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //left intentionally blank.
    }

    public void storePoints(List<Point> points) {
        //need a handle to the database so we can write to the database.
        SQLiteDatabase db = getWritableDatabase();

        //clears all the values from the database.
        ////////////////////////////////////////////////////////
        db.delete(TABLE_NAME, null, null);
        ////////////////////////////////////////////////////////

        int i = 0;
        //loop through each of the Point object passed in.
        for (Point point : points) {
            //ContentValues is part of a mechanism that allows us to write the database in
            //such a way where it uses wildcards (which is supposedly a good idea).
            ContentValues values = new ContentValues();

            //key is the column name.
            //@@@@@@@@@@@@@@@@@@@@@@@@@
            values.put(COL_ID, i);
            values.put(COL_X, point.x);
            values.put(COL_Y, point.y);
            //@@@@@@@@@@@@@@@@@@@@@@@@@

            //INSERT AN ENTRY INTO A TABLE (i.e. TABLE_NAME) OF OUR DATABASE.
            //////////////////////////////////////////////////
            db.insert(TABLE_NAME, null, values);
            //////////////////////////////////////////////////

            //increment the primary key (ID column).
            i++;
        }

        //MUST close the database.
        ///////////
        db.close();
        ///////////
    }

}