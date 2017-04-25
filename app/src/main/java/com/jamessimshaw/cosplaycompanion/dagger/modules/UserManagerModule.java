package com.jamessimshaw.cosplaycompanion.dagger.modules;

import android.content.SharedPreferences;

import com.jamessimshaw.cosplaycompanion.datasources.UserManager;
import com.jamessimshaw.cosplaycompanion.datasources.UserManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by james on 9/20/16.
 */
@Module(includes = {CosplayCompanionAPIModule.class, PreferenceModule.class})
public class UserManagerModule {
    @Provides
    @Singleton
    UserManager provideUserManager(Retrofit retrofit, SharedPreferences preferences) {
        return new UserManagerImpl(retrofit, preferences);
    }
}
