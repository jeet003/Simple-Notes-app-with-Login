package com.jeet.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NoteReaderHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteContract.NoteEntryDb.TABLE_NAME + " (" +
                    NoteContract.NoteEntryDb._ID + " INTEGER PRIMARY KEY," +
                    NoteContract.NoteEntryDb.COLUMN_NAME_TITLE + " TEXT," +
                    NoteContract.NoteEntryDb.COLUMN_NAME_BODY + " TEXT,"+
                    NoteContract.NoteEntryDb.USERNAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteContract.NoteEntryDb.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes_pv.db";

    public NoteReaderHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}
