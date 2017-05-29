package com.jeet.notes;

import android.provider.BaseColumns;



public final class NoteContract {

        private NoteContract () {}

        /* Inner class that defines the table contents */
        public static class NoteEntryDb implements BaseColumns {
            public static final String TABLE_NAME = "notes";
            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_BODY = "body";
            public static final String USERNAME = "username";
        }
}
