package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.BuildConfig;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.models.Convention;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by james on 11/13/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ConventionRecViewAdapterTest {

    private ConventionRecViewAdapter mConventionRecViewAdapter;
    private Activity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(MainActivity.class);
        ArrayList<Convention> conventions = new ArrayList<>();
        conventions.add(new Convention(20, "TestConvention", "A test Convention",
                Uri.parse("localfile.png")));
        conventions.add(new Convention(32, "TestConvention", "A test Convention",
                Uri.parse("localfile.png")));
        conventions.add(new Convention(71, "TestConvention", "A test Convention",
                Uri.parse("localfile.png")));
        mConventionRecViewAdapter = new ConventionRecViewAdapter(conventions, mActivity);
    }

    @Test
    public void testOnCreateViewHolder() throws Exception {
        assertNotNull(mConventionRecViewAdapter.onCreateViewHolder(new LinearLayout(mActivity), 0));
    }

    @Test
    public void testOnBindViewHolder() throws Exception {
        ConventionRecViewAdapter.ViewHolder viewHolder = mConventionRecViewAdapter
                .onCreateViewHolder(new LinearLayout(mActivity), 0);
        mConventionRecViewAdapter.onBindViewHolder(viewHolder, 0);

        assertEquals("TestConvention", ((TextView) viewHolder.itemView
                .findViewById(R.id.convention_name)).getText().toString());
        assertEquals("A test Convention", ((TextView) viewHolder.itemView
                .findViewById(R.id.conDescriptionTextView)).getText().toString());
        assertTrue(((TextView) viewHolder.itemView.findViewById(R.id.conventionEdit))
                .hasOnClickListeners());
        assertTrue(((TextView) viewHolder.itemView.findViewById(R.id.conventionYearsLink))
                .hasOnClickListeners());
    }

    @Test
    public void testGetItemCount() throws Exception {
        assertEquals(3, mConventionRecViewAdapter.getItemCount());
    }
}