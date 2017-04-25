package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.datasources.TokenManager;
import com.jamessimshaw.cosplaycompanion.datasources.UserManager;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.LoginPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.RegisterPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.RegisterPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module for dagger for login dependencies.
 *
 * @author James Simshaw
 */

@Module
public class LoginModule {

    public LoginModule() {

    }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter(Retrofit retrofit, UserManager userManager, TokenManager tokenManager) {
        return new LoginPresenterImpl(retrofit, userManager, tokenManager);
    }

    @Provides
    @Singleton
    public RegisterPresenter provideRegisterPresenter(Retrofit retrofit, UserManager userManager) {
        return new RegisterPresenterImpl(retrofit, userManager);
    }
}
