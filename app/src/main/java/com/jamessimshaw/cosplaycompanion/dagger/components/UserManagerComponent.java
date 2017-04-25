package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.PreferenceModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.UserManagerModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james on 9/21/16.
 */
@Singleton
@Component(modules = { UserManagerModule.class })
public interface UserManagerComponent {
    void inject(MainActivity activity);
}
