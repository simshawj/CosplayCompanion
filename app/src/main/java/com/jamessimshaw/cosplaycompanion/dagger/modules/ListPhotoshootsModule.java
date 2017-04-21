package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module providing the ListPhotoshootPresenter.
 *
 * @author James Simshaw
 */

@Module
public class ListPhotoshootsModule {

    public ListPhotoshootsModule() {

    }

    @Provides
    @Singleton
    public ListPhotoshootsPresenter providePresenter(Retrofit retrofit) {
        return new ListPhotoshootsPresenterImpl(retrofit);
    }
}
