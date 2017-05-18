package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.jamessimshaw.cosplaycompanion.presenters.SuggestionPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.SuggestionPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Suggestions Dagger Module
 *
 * @author James Simshaw
 */

@Module
public class SuggestionModule {

    public SuggestionModule() {

    }

    @Provides
    @Singleton
    public SuggestionPresenter provideSuggestionPresenter(Retrofit retrofit) {
        return new SuggestionPresenterImpl(retrofit);
    }
}
