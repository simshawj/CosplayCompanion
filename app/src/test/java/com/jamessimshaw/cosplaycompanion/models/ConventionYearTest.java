package com.jamessimshaw.cosplaycompanion.models;

import android.os.Parcel;

import com.jamessimshaw.cosplaycompanion.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by james on 11/12/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ConventionYearTest {

    ConventionYear mConventionYear;
    ConventionYear mEqualConventionYear;
    ConventionYear mNotEqualConventionYear;

    @Before
    public void setUp() throws Exception {
        mConventionYear = new ConventionYear(10, new GregorianCalendar(2015, Calendar.OCTOBER, 31).getTime(),
                new GregorianCalendar(2015, Calendar.NOVEMBER, 5).getTime(), 5, "Somewhere");
        mEqualConventionYear = new ConventionYear(10, new GregorianCalendar(2015, Calendar.OCTOBER, 31).getTime(),
                new GregorianCalendar(2015, Calendar.NOVEMBER, 5).getTime(), 5, "Somewhere");
        mNotEqualConventionYear = new ConventionYear(10, new GregorianCalendar(2015, Calendar.OCTOBER, 31).getTime(),
                new GregorianCalendar(2015, Calendar.DECEMBER, 5).getTime(), 5, "Somewhere");

    }

    @Test
    public void testGetYearAsString() throws Exception {
        assertEquals("2015", mConventionYear.getYearAsString());
    }

    @Test
    public void testGetYear() throws Exception {
        assertEquals(2015, mConventionYear.getYear());
    }

    @Test
    public void testWriteToParcel() throws Exception {
        Parcel parcel = Parcel.obtain();
        ConventionYear noIdConventionYear = new ConventionYear(new GregorianCalendar(2015, Calendar.OCTOBER, 31).getTime(),
                new GregorianCalendar(2015, Calendar.NOVEMBER, 5).getTime(), 5, "Somewhere");
        mConventionYear.writeToParcel(parcel, 0);
        noIdConventionYear.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        ConventionYear outConventionYear = ConventionYear.CREATOR.createFromParcel(parcel);
        assertEquals(mConventionYear, outConventionYear);
        outConventionYear = ConventionYear.CREATOR.createFromParcel(parcel);
        assertEquals(noIdConventionYear, outConventionYear);
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(mConventionYear, mConventionYear);
        assertEquals(mConventionYear, mEqualConventionYear);
        assertNotEquals(mConventionYear, mNotEqualConventionYear);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(mConventionYear.hashCode(), mConventionYear.hashCode());
        assertEquals(mConventionYear.hashCode(), mEqualConventionYear.hashCode());
        assertNotEquals(mConventionYear.hashCode(), mNotEqualConventionYear.hashCode());
    }
}