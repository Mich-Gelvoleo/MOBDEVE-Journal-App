package com.mobdeve.s11.gelvoleo.galura.journal.model;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import static com.mobdeve.s11.gelvoleo.galura.journal.model.EntryDbSchema.*;

public class EntryCursorWrapper extends CursorWrapper {

    public EntryCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Entry getEntry() {
        String uuidString = getString(getColumnIndex(EntryTable.Cols.UUID));
        String title = getString(getColumnIndex(EntryTable.Cols.TITLE));
        String caption = getString(getColumnIndex(EntryTable.Cols.CAPTION));
        long date = getLong(getColumnIndex(EntryTable.Cols.DATE));
        int isArchived = getInt(getColumnIndex(EntryTable.Cols.ARCHIVED));
        String filename = getString(getColumnIndex(EntryTable.Cols.FILENAME));

        Entry entry = new Entry(UUID.fromString(uuidString));
        entry.setTitle(title);
        entry.setCaption(caption);
        entry.setDate(new Date(date));
        entry.setArchived(isArchived != 0);
        entry.setFilename(filename);

        return entry;
    }
}
