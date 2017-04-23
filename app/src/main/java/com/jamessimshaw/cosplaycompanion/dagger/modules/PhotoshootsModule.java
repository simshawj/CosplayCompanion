package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenterImpl;

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
public class PhotoshootsModule {

    public PhotoshootsModule() {

    }

    @Provides
    @Singleton
    public ListPhotoshootsPresenter providePresenter(Retrofit retrofit) {
        return new ListPhotoshootsPresenterImpl(retrofit);
    }

    @Provides
    @Singleton
    public ModifyPhotoshootPresenter provideModifyPresenter(Retrofit retrofit) {
        return new ModifyPhotoshootPresenterImpl(retrofit);
    }
}
