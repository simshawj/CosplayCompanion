package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module to provide the ListConventionYearsPresenter.
 *
 * @author James Simshaw
 */

@Module
public class ConventionYearsModule {

    public ConventionYearsModule() {

    }

    @Provides
    @Singleton
    public ListConventionYearsPresenter providePresenter() {
        return new ListConventionYearsPresenterImpl();
    }

    @Provides
    @Singleton
    public ModifyConventionYearPresenter providesModifyPresenter() {
        return  new ModifyConventionYearPresenterImpl();
    }
}
