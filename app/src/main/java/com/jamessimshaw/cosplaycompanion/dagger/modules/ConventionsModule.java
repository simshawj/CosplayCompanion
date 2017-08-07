package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james on 4/17/17.
 */

@Module
public class ConventionsModule {

    public ConventionsModule() {

    }

    @Provides
    @Singleton
    public ListConventionsPresenter provideListConventionsPresenter() {
        return new ListConventionsPresenterImpl();
    }

    @Provides
    @Singleton
    public ModifyConventionPresenter provideModifyConventionPresenter() {
        return new ModifyConventionPresenterImpl();
    }

}
