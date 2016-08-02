package com.jamessimshaw.cosplaycompanion.models;

import android.net.Uri;
import android.os.Parcel;

import com.jamessimshaw.cosplaycompanion.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by james on 11/11/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ConventionTest {

    private Convention mConvention;
    private Convention mNoIdConvention;
    private Convention mEqualConvention;
    private Convention mDifferentConvention;

    @Before
    public void setup() {
        mConvention = new Convention(10, "Test Convention", "Test", Uri.parse("local.png"));
        mNoIdConvention = new Convention("Test Convention", "Test", Uri.parse("local.png"));
        mEqualConvention = new Convention(10, "Test Convention", "Test", Uri.parse("local.png"));
        mDifferentConvention = new Convention(11, "Test Convention", "Test", Uri.parse("local.png"));
    }

    @Test
    public void testWriteToParcel() throws Exception {
        Parcel parcel = Parcel.obtain();
        mConvention.writeToParcel(parcel, 0);
        mNoIdConvention.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Convention outConvention = Convention.CREATOR.createFromParcel(parcel);
        assertEquals(mConvention, outConvention);
        outConvention = Convention.CREATOR.createFromParcel(parcel);
        assertEquals(mNoIdConvention, outConvention);
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(mConvention, mConvention);
        assertEquals(mConvention, mEqualConvention);
        assertNotEquals(mConvention, mDifferentConvention);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(mConvention.hashCode(), mConvention.hashCode());
        assertEquals(mConvention.hashCode(), mEqualConvention.hashCode());
        assertNotEquals(mConvention.hashCode(), mDifferentConvention.hashCode());
    }
}