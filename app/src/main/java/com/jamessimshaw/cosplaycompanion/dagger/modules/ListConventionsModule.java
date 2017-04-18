package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenterImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by james on 4/17/17.
 */

@Module
public class ListConventionsModule {

    public ListConventionsModule() {

    }

    @Provides
    @Singleton
    public ListConventionsPresenter provideListConventionsPresenter(@Named("conventions") Retrofit retrofit) {
        return new ListConventionsPresenterImpl(retrofit);
    }

}
