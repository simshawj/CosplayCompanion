package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;

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
public class ListConventionYearsModule {

    public ListConventionYearsModule() {

    }

    @Provides
    @Singleton
    public ListConventionYearsPresenter providePresenter(Retrofit retrofit) {
        return new ListConventionYearsPresenterImpl(retrofit);
    }
}
