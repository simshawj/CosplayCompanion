package com.jamessimshaw.cosplaycompanion;

import android.app.Application;

import com.jamessimshaw.cosplaycompanion.dagger.components.ConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerListPhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.ConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.ListPhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.NetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.PreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ApplicationModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ListPhotoshootsModule;

/**
 * Application for Cosplay Companion.
 *
 * @author James Simshaw
 */
public class CosplayCompanionApplication extends Application {
    private NetworkComponent mNetworkComponent;
    private static PreferenceComponent mPreferenceComponent; //TODO: Find a better way
    private ConventionsComponent mConventionsComponent;
    private ConventionYearsComponent mConventionYearsComponent;
    private ListPhotoshootsComponent mListPhotoshootsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferenceComponent = DaggerPreferenceComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mNetworkComponent = DaggerNetworkComponent.builder().build();
        mConventionsComponent = DaggerConventionsComponent.builder()
                .conventionsModule(new ConventionsModule())
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule()).build();
        mConventionYearsComponent = DaggerConventionYearsComponent.builder()
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule()).build();
        mListPhotoshootsComponent = DaggerListPhotoshootsComponent.builder()
                .listPhotoshootsModule(new ListPhotoshootsModule())
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule()).build();
    }

    public static PreferenceComponent getPreferenceComponent() {
        return mPreferenceComponent;
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public ConventionsComponent getConventionsComponent() {
        return mConventionsComponent;
    }

    public ConventionYearsComponent getConventionYearsComponent() {
        return mConventionYearsComponent;
    }

    public ListPhotoshootsComponent getListPhotoshootsComponent() {
        return mListPhotoshootsComponent;
    }

}
