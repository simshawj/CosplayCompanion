package com.jamessimshaw.cosplaycompanion.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.controllers.ListConventionsController;

public class MainActivity extends AppCompatActivity {

    private Router mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ViewGroup container = (ViewGroup) findViewById(R.id.fragment_container_main);

        mRouter = Conductor.attachRouter(this, container, savedInstanceState);
        if (!mRouter.hasRootController()) {
            mRouter.setRoot(RouterTransaction.with(ListConventionsController.newInstance()));
        }
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//                finish();
//            } else {
//                super.onBackPressed();
//            }
//        }
        if (!mRouter.handleBack()) {
            super.onBackPressed();
        }
    }
}
