package com.mobdeve.s11.gelvoleo.galura.journal.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mobdeve.s11.gelvoleo.galura.journal.model.EntryDbSchema.EntryTable.*;

public class EntryLab {
    private static EntryLab sEntryLab;
    private Context context;
    private SQLiteDatabase database;

    public static EntryLab get(Context context) {
        if (sEntryLab == null) {
            sEntryLab = new EntryLab(context);
        }

        return sEntryLab;
    }

    public void deleteEntry(Entry entry) {
        database.delete(
                EntryDbSchema.EntryTable.NAME,
                EntryDbSchema.EntryTable.Cols.UUID + "=?",
                new String[] {entry.getId().toString()}
        );
    }

    private EntryLab(Context context) {
        this.context = context.getApplicationContext();
        this.database = new EntriesDatabase(context)
                .getWritableDatabase();
    }

    public void addEntry(Entry entry) {
        ContentValues values = getContentValues(entry);
        database.insert(NAME, null, values);
    }

    //TODO: add clause that fetches only unarchived entries
    public List<Entry> getEntries(int isArchived) {
        List<Entry> entries = new ArrayList<>();
        String clause = Cols.ARCHIVED + " = " + isArchived;
        EntryCursorWrapper cursor = queryEntries(clause, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                entries.add(cursor.getEntry());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return entries;
    }

    public void deleteArchivedOnTime() {
        database.delete(
                EntryDbSchema.EntryTable.NAME,
                "archived = 1 AND archived_date <= date('now', '-2 minutes')",
                null
        );
    }

    public Entry getEntry(UUID id) {
        EntryCursorWrapper cursor = queryEntries(
                EntryDbSchema.EntryTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEntry();
        } finally {
            cursor.close();
        }
    }

    public void updateEntry(Entry entry) {
        String uuidString = entry.getId().toString();
        ContentValues values = getContentValues(entry);
        database.update(EntryDbSchema.EntryTable.NAME, values,
                EntryDbSchema.EntryTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private EntryCursorWrapper queryEntries(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(
                NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new EntryCursorWrapper(cursor);
    }

    public File getPhotoFile(Entry entry) {
        File filesDir = context.getFilesDir();
        return new File(filesDir, entry.getFilename());
    }

    private static ContentValues getContentValues(Entry entry) {
        ContentValues values = new ContentValues();
        values.put(Cols.UUID, entry.getId().toString());
        values.put(Cols.TITLE, entry.getTitle());
        values.put(Cols.CAPTION, entry.getCaption());
        values.put(Cols.DATE, entry.getDate().getTime());
        values.put(Cols.ARCHIVED, entry.isArchived() ? 1 : 0);
        values.put(Cols.FILENAME, entry.getFilename());
        values.put(Cols.TAGS, entry.getTags());
        values.put(Cols.LOCATION, entry.getLocation());
        values.put(Cols.ARCHIVED_DATE, entry.getArchivedDate().getTime());

        return values;
    }
}
