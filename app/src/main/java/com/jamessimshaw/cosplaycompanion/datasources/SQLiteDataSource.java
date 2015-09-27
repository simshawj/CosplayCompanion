package com.jamessimshaw.cosplaycompanion.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.nio.ByteBuffer;

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
        int bytes = convention.getLogo().getByteCount();    // number of bytes for the buffer
        ByteBuffer buffer = ByteBuffer.allocate(bytes);     // creates a buffer to store img
        convention.getLogo().copyPixelsToBuffer(buffer);    // copies img to buffer

        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(mSQLiteHelper.COLUMN_NAME, convention.getName());
        values.put(mSQLiteHelper.COLUMN_DESCRIPTION, convention.getDescription());
        values.put(mSQLiteHelper.COLUMN_LOGO, buffer.array());
        convention.setId(database.insert(SQLiteHelper.TABLE_CONVENTIONS, null, values));

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void create(ConventionYear conventionYear) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(mSQLiteHelper.COLUMN_DATE, conventionYear.getDate().getTime());
        values.put(mSQLiteHelper.COLUMN_DAYS, conventionYear.getDays());
        values.put(mSQLiteHelper.COLUMN_CONVENTION, conventionYear.getConventionId());
        conventionYear.setId(database.insert(SQLiteHelper.TABLE_CONVENTION_YEARS, null, values));

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void create(Photoshoot photoshoot) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(mSQLiteHelper.COLUMN_SERIES, photoshoot.getSeries());
        values.put(mSQLiteHelper.COLUMN_START, photoshoot.getStart().getTime());
        values.put(mSQLiteHelper.COLUMN_LOCATION, photoshoot.getLocation());
        values.put(mSQLiteHelper.COLUMN_DESCRIPTION, photoshoot.getDescription());
        values.put(mSQLiteHelper.COLUMN_CONVENTION_YEAR, photoshoot.getConventionYearId());
        photoshoot.setId(database.insert(SQLiteHelper.TABLE_PHOTOSHOOTS, null, values));

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }
}
