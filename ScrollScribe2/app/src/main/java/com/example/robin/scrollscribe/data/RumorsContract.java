package com.example.robin.scrollscribe.data;

import android.provider.BaseColumns;


public class RumorsContract {
    public static final class RumorsEntry implements BaseColumns {
        public static final String TABLE_NAME = "rumors";
        public static final String COLUMN_RNAME = "rumorName";
        public static final String COLUMN_RSUMMERY = "rumorSummery";
        public static final String USERID = "storedUserID";
    }

    public static final class PeopleEntry implements BaseColumns {
        public static final String TABLE_NAME = "people";
        public static final String COLUMN_PNAME = "peopleName";
        public static final String COLUMN_PAGE = "peopleAge";
        public static final String COLUMN_PRANK = "peopleRank";
        public static final String COLUMN_PRELATION = "peopleRelation";
        public static final String COLUMN_PSUMMERY = "peopleSummery";
        public static final String USERID = "storedUserID";
    }
}
