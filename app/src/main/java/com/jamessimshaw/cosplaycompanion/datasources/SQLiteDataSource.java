package com.jamessimshaw.cosplaycompanion.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by james on 9/26/15.
 * SQLite is going to be used for development only
 */
public class SQLiteDataSource {
    private SQLiteHelper mSQLiteHelper;
    private Context mContext;

    public SQLiteDataSource(Context context) {
        mContext = context;
        mSQLiteHelper = new SQLiteHelper(context);
    }

    private SQLiteDatabase open() {
        return mSQLiteHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database) {
        database.close();
    }

    public void create(Convention convention) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        convention.getLogo().compress(Bitmap.CompressFormat.PNG, 0, stream);

        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NAME, convention.getName());
        values.put(SQLiteHelper.COLUMN_DESCRIPTION, convention.getDescription());
        values.put(SQLiteHelper.COLUMN_LOGO, stream.toByteArray());
        convention.setId(database.insert(SQLiteHelper.TABLE_CONVENTIONS, null, values));

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void create(ConventionYear conventionYear) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_DATE, conventionYear.getStartDate().getTime());
        values.put(SQLiteHelper.COLUMN_DAYS, conventionYear.getEndDate().getTime());
        values.put(SQLiteHelper.COLUMN_CONVENTION, conventionYear.getConventionId());
        values.put(SQLiteHelper.COLUMN_LOCATION, conventionYear.getLocation());
        conventionYear.setId(database.insert(SQLiteHelper.TABLE_CONVENTION_YEARS, null, values));

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void create(Photoshoot photoshoot) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_SERIES, photoshoot.getSeries());
        values.put(SQLiteHelper.COLUMN_START, photoshoot.getStart().getTime());
        values.put(SQLiteHelper.COLUMN_LOCATION, photoshoot.getLocation());
        values.put(SQLiteHelper.COLUMN_DESCRIPTION, photoshoot.getDescription());
        values.put(SQLiteHelper.COLUMN_CONVENTION_YEAR, photoshoot.getConventionYearId());
        photoshoot.setId(database.insert(SQLiteHelper.TABLE_PHOTOSHOOTS, null, values));

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public ArrayList<Convention> read() {
        SQLiteDatabase database = open();

        Cursor cursor = database.query(SQLiteHelper.TABLE_CONVENTIONS,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Convention> conventions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                byte[] array = getBlobFromColumnName(cursor, SQLiteHelper.COLUMN_LOGO);
                Bitmap logo = BitmapFactory.decodeByteArray(array, 0, array.length);
                Convention convention = new Convention(
                        getLongFromColumnName(cursor, BaseColumns._ID),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_NAME),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_DESCRIPTION),
                        logo);
                conventions.add(convention);
            } while(cursor.moveToNext());
        }
        cursor.close();
        close(database);
        return conventions;
    }

    public ArrayList<ConventionYear> read(Convention convention) {
        SQLiteDatabase database = open();

        Cursor cursor = database.query(SQLiteHelper.TABLE_CONVENTION_YEARS,
                null,
                SQLiteHelper.COLUMN_CONVENTION + " = ?",
                new String[]{Long.toString(convention.getId())},
                null,
                null,
                null,
                null
        );

        ArrayList<ConventionYear> conventionYears = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ConventionYear conventionYear = new ConventionYear(
                        getLongFromColumnName(cursor, BaseColumns._ID),
                        new Date(getLongFromColumnName(cursor, SQLiteHelper.COLUMN_DATE)),
                        new Date(getLongFromColumnName(cursor, SQLiteHelper.COLUMN_DAYS)),
                        getLongFromColumnName(cursor, SQLiteHelper.COLUMN_CONVENTION),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_LOCATION)
                );
                conventionYears.add(conventionYear);
            } while(cursor.moveToNext());
        }

        return conventionYears;
    }

    public ArrayList<Photoshoot> read(ConventionYear conventionYear) {
        SQLiteDatabase database = open();

        Cursor cursor = database.query(SQLiteHelper.TABLE_PHOTOSHOOTS,
                null,
                SQLiteHelper.COLUMN_CONVENTION_YEAR + " = ?",
                new String[]{Long.toString(conventionYear.getId())},
                null,
                null,
                null,
                null
        );

        ArrayList<Photoshoot> photoshoots = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Photoshoot photoshoot = new Photoshoot(
                        getLongFromColumnName(cursor, BaseColumns._ID),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_SERIES),
                        new Date(getLongFromColumnName(cursor, SQLiteHelper.COLUMN_START)),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_LOCATION),
                        getStringFromColumnName(cursor, SQLiteHelper.COLUMN_DESCRIPTION),
                        getLongFromColumnName(cursor, SQLiteHelper.COLUMN_CONVENTION_YEAR)
                );
                photoshoots.add(photoshoot);
            } while(cursor.moveToNext());
        }
        return photoshoots;
    }

    private long getLongFromColumnName(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private byte[] getBlobFromColumnName(Cursor cursor, String columnName) {
        return cursor.getBlob(cursor.getColumnIndex(columnName));
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
