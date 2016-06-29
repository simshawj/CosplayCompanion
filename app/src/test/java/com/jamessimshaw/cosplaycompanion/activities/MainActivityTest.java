package com.jamessimshaw.cosplaycompanion.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.jamessimshaw.cosplaycompanion.BuildConfig;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionFragment;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by james on 11/13/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private MainActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void testOnCreate() throws Exception {
        assertNotNull(mActivity);
        assertNotNull(mActivity.getSupportActionBar());
        assertFalse(mActivity.getSupportFragmentManager().getFragments().isEmpty());
    }

    @Test
    public void testOnModifyFragmentInteraction() throws Exception {
        int numberOfFragments = mActivity.getSupportFragmentManager().getBackStackEntryCount();
        Fragment fragment = ModifyConventionFragment.newInstance();
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .addToBackStack(null)
                .commit();
        assertEquals(numberOfFragments + 1, mActivity.getSupportFragmentManager().getBackStackEntryCount());
        mActivity.onModifyFragmentInteraction();
        assertEquals(numberOfFragments, mActivity.getSupportFragmentManager().getBackStackEntryCount());
    }

    @Test
    public void testOnFragmentInteraction() throws Exception {
        Convention convention = new Convention(20, "TestConvention", "A test Convention",
                Uri.parse("localfile.png"));
        ConventionYear conventionYear = new ConventionYear(21, new GregorianCalendar(2015, Calendar.OCTOBER, 31).getTime(),
                new GregorianCalendar(2015, Calendar.NOVEMBER, 5).getTime(), convention.getId(),
                "Somewhere", "Con 2015");
        Photoshoot photoshoot = new Photoshoot(22, "Test Series", new GregorianCalendar(2015, Calendar.NOVEMBER, 2).getTime(),
                "Middle of Nowhere", "A test photo shoot", conventionYear.getId());
        int numberofFragments = mActivity.getSupportFragmentManager().getFragments().size();

        Map<String, Object> items = createInvalidMap(convention, conventionYear, photoshoot);
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            mActivity.onFragmentInteraction(entry.getKey(), entry.getValue());
        }
        assertEquals(numberofFragments, mActivity.getSupportFragmentManager().getFragments().size());
        items = createValidMap(convention, conventionYear, photoshoot);
        for (Map.Entry<String, Object> entry : items.entrySet()) {
            mActivity.onFragmentInteraction(entry.getKey(), entry.getValue());
            numberofFragments++;
            assertEquals(numberofFragments, mActivity.getSupportFragmentManager().getFragments().size());
        }
    }

    private Map<String, Object> createInvalidMap(Convention convention, ConventionYear conventionYear, Photoshoot photoshoot) {
        Map<String, Object> map = new HashMap<>();

        map.put("Nonevent", "Nonevent");
        map.put("Nonevent", convention);
        map.put("Nonevent", conventionYear);
        map.put("Nonevent", photoshoot);
        map.put("show convention", "String");
        map.put("show conventionYear", "String");
        map.put("create conventionYear", "String");
        map.put("create photoshoot", "String");
        map.put("edit convention", "String");
        map.put("edit conventionYear", "String");
        map.put("edit photoshoot", "String");

        return map;
    }

    private Map<String,Object> createValidMap(Convention convention, ConventionYear conventionYear, Photoshoot photoshoot) {
        Map<String, Object> map = new HashMap<>();

        map.put("show convention", convention);
        map.put("show conventionYear", conventionYear);
        map.put("create convention", null);
        map.put("create conventionYear", convention);
        map.put("create photoshoot", conventionYear);
        map.put("edit convention", convention);
        map.put("edit conventionYear", conventionYear);
        map.put("edit photoshoot", photoshoot);

        return map;
    }
}