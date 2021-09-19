package com.mobdeve.s11.gelvoleo.galura.journal.model;

public class EntryDbSchema {
    public static final class EntryTable {
        public static final String NAME = "entries";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String CAPTION = "caption";
            public static final String DATE = "date";
            public static final String ARCHIVED = "archived";
            public static final String FILENAME = "filename";
            public static final String TAGS = "tags";
            public static final String LOCATION = "location";
        }
    }
}
