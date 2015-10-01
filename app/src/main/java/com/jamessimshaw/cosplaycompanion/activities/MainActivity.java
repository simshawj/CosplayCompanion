package com.jamessimshaw.cosplaycompanion.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.adapters.NavAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.navigationDrawer);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerViewAdapter = new NavAdapter(getResources().getStringArray(R.array.nav_drawer_menu_items),
                getResources().getIntArray(R.array.nav_drawer_menu_icons));

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.navDrawerLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
