package com.mobdeve.s11.gelvoleo.galura.journal.utils;

import com.mobdeve.s11.gelvoleo.galura.journal.model.Entry;

import java.util.ArrayList;

public class DataHelper {
    private static DataHelper INSTANCE = null;
    private static ArrayList<Entry> entries;

    public DataHelper(){
        entries = new ArrayList<>();

        entries.add(new Entry(
                "Mili",
                "Nobody is born a fighter. There isn't enough space for all of us"
        ));

        entries.add(new Entry(
                "Ascension Materials Needed",
                "Don't forget to farm this Sunday!"
        ));

        entries.add(new Entry(
                "The First Hunter",
                "Dear oh dear, what was it? The Hunt, the blood, or the horrible dream?"
        ));
    }

    public static DataHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataHelper();
        }

        return (INSTANCE);
    }

    public static ArrayList<Entry> getData() {
        if (INSTANCE != null) {
            return entries;
        }

        return null;
    }
}
