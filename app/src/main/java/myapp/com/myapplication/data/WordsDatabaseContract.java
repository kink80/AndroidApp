package myapp.com.myapplication.data;

import android.provider.BaseColumns;

/**
 * Created by stecl on 22/12/14.
 */
public final class WordsDatabaseContract {

    public WordsDatabaseContract() {};

    public static abstract class WordEntry implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_NAME_ORIGINAL = "original";
        public static final String COLUMN_NAME_TRANSLATION = "translation";
        public static final String COLUMN_NAME_PROBABILITY = "level";
    }


}
