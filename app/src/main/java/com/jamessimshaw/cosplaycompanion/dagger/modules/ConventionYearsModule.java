package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

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
    public ListConventionYearsPresenter providePresenter(Retrofit retrofit) {
        return new ListConventionYearsPresenterImpl(retrofit);
    }

    @Provides
    @Singleton
    public ModifyConventionYearPresenter providesModifyPresenter() {
        return  new ModifyConventionYearPresenterImpl();
    }
}
