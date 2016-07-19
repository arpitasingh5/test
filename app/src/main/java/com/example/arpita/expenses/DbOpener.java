package com.example.arpita.expenses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arpita.expenses.db.ExpenseTable;

/**
 * Created by arpita on 13/07/16.
 */
public class DbOpener extends SQLiteOpenHelper {

    public static final String DB_NAME="database";
    public static final int DB_VER=1;

    private static DbOpener dbOpener=null;

    public DbOpener(Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    public  static  SQLiteDatabase openReadableDatabase(Context c){
        if(dbOpener==null){
            dbOpener=new DbOpener(c);
        }
        return dbOpener.getReadableDatabase();
    }

    public  static  SQLiteDatabase openWriteableDatabase(Context c){
        if(dbOpener==null){
            dbOpener=new DbOpener(c);
        }
        return dbOpener.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ExpenseTable.TABLE_CREATE_CMD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
