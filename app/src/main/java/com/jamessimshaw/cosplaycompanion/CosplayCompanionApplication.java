package com.jamessimshaw.cosplaycompanion;

import android.app.Application;

import com.jamessimshaw.cosplaycompanion.dagger.components.ConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerLoginComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.ConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerUserManagerComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.LoginComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.PhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.PreferenceComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.UserManagerComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ApplicationModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionYearsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.PhotoshootsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.PreferenceModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.UserManagerModule;

/**
 * Application for Cosplay Companion.
 *
 * @author James Simshaw
 */
public class CosplayCompanionApplication extends Application {
    private static PreferenceComponent mPreferenceComponent; //TODO: Find a better way
    private ConventionsComponent mConventionsComponent;
    private ConventionYearsComponent mConventionYearsComponent;
    private PhotoshootsComponent mPhotoshootsComponent;
    private LoginComponent mLoginComponent;
    private UserManagerComponent mUserManagerComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferenceComponent = DaggerPreferenceComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mUserManagerComponent = DaggerUserManagerComponent.builder()
                .userManagerModule(new UserManagerModule())
                .applicationModule(new ApplicationModule(this))
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule())
                .preferenceModule(new PreferenceModule()).build();
        mLoginComponent = DaggerLoginComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule())
                .preferenceModule(new PreferenceModule())
                .userManagerModule(new UserManagerModule()).build();
        mConventionsComponent = DaggerConventionsComponent.builder()
                .conventionsModule(new ConventionsModule())
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule()).build();
        mConventionYearsComponent = DaggerConventionYearsComponent.builder()
                .conventionYearsModule(new ConventionYearsModule())
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule()).build();
        mPhotoshootsComponent = DaggerPhotoshootsComponent.builder()
                .photoshootsModule(new PhotoshootsModule())
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule()).build();
    }

    public static PreferenceComponent getPreferenceComponent() {
        return mPreferenceComponent;
    }

    public UserManagerComponent getUserManagerComponent() {
        return mUserManagerComponent;
    }

    public LoginComponent getLoginComponent() {
        return mLoginComponent;
    }

    public ConventionsComponent getConventionsComponent() {
        return mConventionsComponent;
    }

    public ConventionYearsComponent getConventionYearsComponent() {
        return mConventionYearsComponent;
    }

    public PhotoshootsComponent getPhotoshootsComponent() {
        return mPhotoshootsComponent;
    }

}
