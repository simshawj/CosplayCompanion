package com.jamessimshaw.cosplaycompanion;

import android.app.Application;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerUserComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.NetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.PreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.UserComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ApplicationModule;

/**
 * Created by james on 8/24/16.
 */
public class CosplayCompanionApplication extends Application {
    NetworkComponent mNetworkComponent;
    UserComponent mUserComponent;
    static PreferenceComponent mPreferenceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferenceComponent = DaggerPreferenceComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mNetworkComponent = DaggerNetworkComponent.builder().build();
        mUserComponent = DaggerUserComponent.builder().build();
    }

    public static PreferenceComponent getPreferenceComponent() {
        return mPreferenceComponent;
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public UserComponent getUserComponent() {
        return mUserComponent;
    }
}
