package com.jamessimshaw.cosplaycompanion;

import android.app.Application;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerListConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerListConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerListPhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.ListConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.ListConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.ListPhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.NetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.PreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ApplicationModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ListConventionYearsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ListConventionsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ListPhotoshootsModule;

/**
 * Application for Cosplay Companion.
 *
 * @author James Simshaw
 */
public class CosplayCompanionApplication extends Application {
    private NetworkComponent mNetworkComponent;
    private static PreferenceComponent mPreferenceComponent; //TODO: Find a better way
    private ListConventionsComponent mListConventionsComponent;
    private ListConventionYearsComponent mListConventionYearsComponent;
    private ListPhotoshootsComponent mListPhotoshootsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferenceComponent = DaggerPreferenceComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mNetworkComponent = DaggerNetworkComponent.builder().build();
        mListConventionsComponent = DaggerListConventionsComponent.builder()
                .listConventionsModule(new ListConventionsModule())
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule()).build();
        mListConventionYearsComponent = DaggerListConventionYearsComponent.builder()
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

    public ListConventionsComponent getListConventionsComponent() {
        return mListConventionsComponent;
    }

    public ListConventionYearsComponent getListConventionYearsComponent() {
        return mListConventionYearsComponent;
    }

    public ListPhotoshootsComponent getListPhotoshootsComponent() {
        return mListPhotoshootsComponent;
    }

}
