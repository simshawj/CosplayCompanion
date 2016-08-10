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
public class PhotoshootTest {

    private Photoshoot mPhotoshoot;
    private Photoshoot mEqualPhotoshoot;
    private Photoshoot mNotEqualPhotoshoot;

    @Before
    public void setUp() throws Exception {
        mPhotoshoot = new Photoshoot(10, "Test Series", new GregorianCalendar(2015, Calendar.NOVEMBER, 2).getTime(),
                "Middle of Nowhere", "A test photo shoot", 5);
        mEqualPhotoshoot = new Photoshoot(10, "Test Series", new GregorianCalendar(2015, Calendar.NOVEMBER, 2).getTime(),
                "Middle of Nowhere", "A test photo shoot", 5);
        mNotEqualPhotoshoot = new Photoshoot(10, "Different Series", new GregorianCalendar(2015, Calendar.NOVEMBER, 2).getTime(),
                "Middle of Nowhere", "A test photo shoot", 5);
    }

    @Test
    public void testWriteToParcel() throws Exception {
        Photoshoot noIdPhotoshoot = new Photoshoot("Test Series", new GregorianCalendar(2015, Calendar.NOVEMBER, 2).getTime(),
                "Middle of Nowhere", "A test photo shoot", 5);
        Parcel parcel = Parcel.obtain();
        mPhotoshoot.writeToParcel(parcel, 0);
        noIdPhotoshoot.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Photoshoot outputPhotoshoot = Photoshoot.CREATOR.createFromParcel(parcel);
        assertEquals(mPhotoshoot, outputPhotoshoot);
        outputPhotoshoot = Photoshoot.CREATOR.createFromParcel(parcel);
        assertEquals(noIdPhotoshoot, outputPhotoshoot);
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(mPhotoshoot, mPhotoshoot);
        assertEquals(mPhotoshoot, mEqualPhotoshoot);
        assertNotEquals(mPhotoshoot, mNotEqualPhotoshoot);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(mPhotoshoot.hashCode(), mPhotoshoot.hashCode());
        assertEquals(mPhotoshoot.hashCode(), mEqualPhotoshoot.hashCode());
        assertNotEquals(mPhotoshoot.hashCode(), mNotEqualPhotoshoot.hashCode());
    }
}