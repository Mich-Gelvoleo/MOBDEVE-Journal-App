package com.mobdeve.s11.gelvoleo.galura.journal.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobdeve.s11.gelvoleo.galura.journal.model.EntryDbSchema;

import static com.mobdeve.s11.gelvoleo.galura.journal.model.EntryDbSchema.*;

public class EntriesDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "entries.db";

    public EntriesDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + EntryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                EntryTable.Cols.UUID + ", " +
                EntryTable.Cols.TITLE + ", " +
                EntryTable.Cols.CAPTION + ", " +
                EntryTable.Cols.DATE + ", " +
                EntryTable.Cols.FILENAME + ", " +
                EntryTable.Cols.ARCHIVED + ", " +
                EntryTable.Cols.TAGS + ", " +
                EntryTable.Cols.LOCATION +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
