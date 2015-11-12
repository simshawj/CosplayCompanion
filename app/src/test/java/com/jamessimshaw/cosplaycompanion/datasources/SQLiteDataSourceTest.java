package com.jamessimshaw.cosplaycompanion.datasources;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.jamessimshaw.cosplaycompanion.BuildConfig;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by james on 11/11/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SQLiteDataSourceTest {

    SQLiteDataSource mDataSource;
    Convention mConvention;
    ConventionYear mConventionYear;
    Photoshoot mPhotoshoot;

    @Before
    public void setup() {
        mDataSource = new SQLiteDataSource(RuntimeEnvironment.application);
        mConvention = new Convention("TestConvention", "A test Convention",
                Uri.parse("localfile.png"));
        mDataSource.create(mConvention);
        mConventionYear = new ConventionYear(new GregorianCalendar(2015, Calendar.OCTOBER, 31).getTime(),
                new GregorianCalendar(2015, Calendar.NOVEMBER, 5).getTime(), mConvention.getId(), "Somewhere");
        mDataSource.create(mConventionYear);
        mPhotoshoot = new Photoshoot("Test Series", new GregorianCalendar(2015, Calendar.NOVEMBER, 2).getTime(),
                "Middle of Nowhere", "A test photo shoot", mConventionYear.getId());
        mDataSource.create(mPhotoshoot);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testReadConvention() throws Exception {
        ArrayList<Convention> conventions = mDataSource.read();
        assertEquals(mConvention.getDescription(), conventions.get(0).getDescription());
        assertEquals(mConvention.getLogoUri(), conventions.get(0).getLogoUri());
        assertEquals(mConvention.getName(), conventions.get(0).getName());
        assertEquals(mConvention.getId(), conventions.get(0).getId());
    }

    @Test
    public void testUpdateAndReadConvention() throws Exception {
        mConvention.setDescription("Modified");
        long originalId = mConvention.getId();
        mDataSource.update(mConvention);
        ArrayList<Convention> conventions = mDataSource.read();
        assertEquals(mConvention.getDescription(), conventions.get(0).getDescription());
        assertEquals(originalId, conventions.get(0).getId());
    }

    @Test
    public void testReadConventionYear() throws Exception {
        ArrayList<ConventionYear> conventionYears = mDataSource.read(mConvention);
        assertEquals(mConventionYear.getStartDate(), conventionYears.get(0).getStartDate());
        assertEquals(mConventionYear.getEndDate(), conventionYears.get(0).getEndDate());
        assertEquals(mConventionYear.getLocation(), conventionYears.get(0).getLocation());
        assertEquals(mConventionYear.getConventionId(), conventionYears.get(0).getConventionId());
        assertEquals(mConventionYear.getId(), conventionYears.get(0).getId());
    }

    @Test
    public void testUpdateAndReadConventionYear() throws Exception {
        mConventionYear.setLocation("Some other place in the world");
        long originalId = mConventionYear.getId();
        mDataSource.update(mConventionYear);
        ArrayList<ConventionYear> conventionYears = mDataSource.read(mConvention);
        assertEquals(mConventionYear.getLocation(), conventionYears.get(0).getLocation());
        assertEquals(originalId, conventionYears.get(0).getId());
    }

    @Test
    public void testReadPhotoshoot() throws Exception {
        ArrayList<Photoshoot> photoshoots = mDataSource.read(mConventionYear);
        assertEquals(mPhotoshoot.getLocation(), photoshoots.get(0).getLocation());
        assertEquals(mPhotoshoot.getDescription(), photoshoots.get(0).getDescription());
        assertEquals(mPhotoshoot.getSeries(), photoshoots.get(0).getSeries());
        assertEquals(mPhotoshoot.getStart(), photoshoots.get(0).getStart());
        assertEquals(mPhotoshoot.getConventionYearId(), photoshoots.get(0).getConventionYearId());
        assertEquals(mPhotoshoot.getId(), photoshoots.get(0).getId());
    }

    @Test
    public void testUpdateAndReadPhotoshoot() throws Exception {
        mPhotoshoot.setLocation("Somewhere Else");
        long originalId = mPhotoshoot.getId();
        mDataSource.update(mPhotoshoot);
        ArrayList<Photoshoot> photoshoots = mDataSource.read(mConventionYear);
        assertEquals(mPhotoshoot.getLocation(), photoshoots.get(0).getLocation());
        assertEquals(originalId, photoshoots.get(0).getId());
    }
}