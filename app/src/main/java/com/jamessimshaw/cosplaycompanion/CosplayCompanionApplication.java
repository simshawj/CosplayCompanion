package com.jamessimshaw.cosplaycompanion;

import android.app.Application;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerUserComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.NetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.UserComponent;

/**
 * Created by james on 8/24/16.
 */
public class CosplayCompanionApplication extends Application {
    NetworkComponent mNetworkComponent;
    UserComponent mUserComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkComponent = DaggerNetworkComponent.builder().build();
        mUserComponent = DaggerUserComponent.builder().build();
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public UserComponent getUserComponent() {
        return mUserComponent;
    }
}
