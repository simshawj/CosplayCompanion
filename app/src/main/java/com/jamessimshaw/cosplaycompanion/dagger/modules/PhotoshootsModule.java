package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListPhotoshootsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    public ListPhotoshootsPresenter providePresenter() {
        return new ListPhotoshootsPresenterImpl();
    }

    @Provides
    @Singleton
    public ModifyPhotoshootPresenter provideModifyPresenter() {
        return new ModifyPhotoshootPresenterImpl();
    }
}
