package com.jamessimshaw.cosplaycompanion.datasources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by james on 9/25/15.
 * Will not be used in the final version, but is useful for local development.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cosplay_companion.db";
    private static final int DB_VERSION = 3;

    public static final String TABLE_CONVENTIONS = "conventions";
    public static final String TABLE_CONVENTION_YEARS = "convention_years";
    public static final String TABLE_PHOTOSHOOTS = "photoshoots";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LOGO = "logo";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_CONVENTION = "convention";
    public static final String COLUMN_SERIES = "series";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_CONVENTION_YEAR = "convention_year";

    private static final String CONVENTIONS_CREATE = "create table " +
            TABLE_CONVENTIONS + " (" + BaseColumns._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
            " TEXT NOT NULL, " + COLUMN_DESCRIPTION +
            " TEXT," + COLUMN_LOGO +
            " BLOB)";

    private static final String CONVENTION_YEARS_CREATE = "create table " +
            TABLE_CONVENTION_YEARS + " (" + BaseColumns._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE +
            " INTEGER NOT NULL, " + COLUMN_DAYS +
            " INTEGER NOT NULL, " + COLUMN_CONVENTION +
            " INTEGER NOT NULL, " + COLUMN_LOCATION +
            " TEXT NOT NULL, " +
            " FOREIGN KEY(" + COLUMN_CONVENTION + ") REFERENCES " + TABLE_CONVENTIONS +
            "(" + BaseColumns._ID + "))";

    private static final String PHOTOSHOOTS_CREATE = "create table " +
            TABLE_PHOTOSHOOTS + " (" + BaseColumns._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SERIES +
            " TEXT NOT NULL, " + COLUMN_START +
            " INTEGER NOT NULL, " + COLUMN_LOCATION +
            " TEXT NOT NULL, " + COLUMN_DESCRIPTION +
            " TEXT, " + COLUMN_CONVENTION_YEAR +
            " INTEGER NOT NULL, " +
            " FOREIGN KEY(" + COLUMN_CONVENTION_YEAR + ") REFERENCES " + TABLE_CONVENTION_YEARS +
            "(" + BaseColumns._ID + "))";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CONVENTIONS_CREATE);
        db.execSQL(CONVENTION_YEARS_CREATE);
        db.execSQL(PHOTOSHOOTS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Since this is for development only, old data does not matter
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOSHOOTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVENTION_YEARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVENTIONS);
        onCreate(db);
    }
}
