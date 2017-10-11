package com.jamessimshaw.cosplaycompanion;

import android.app.Application;

import com.jamessimshaw.cosplaycompanion.dagger.components.ConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.ConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerConventionYearsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerConventionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerSuggestionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.PhotoshootsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.components.SuggestionsComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionYearsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.PhotoshootsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.SuggestionModule;

/**
 * Application for Cosplay Companion.
 *
 * @author James Simshaw
 */
public class CosplayCompanionApplication extends Application {
    private ConventionsComponent mConventionsComponent;
    private ConventionYearsComponent mConventionYearsComponent;
    private PhotoshootsComponent mPhotoshootsComponent;
    private SuggestionsComponent mSuggestionsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mConventionsComponent = DaggerConventionsComponent.builder()
                .conventionsModule(new ConventionsModule())
                .build();
        mConventionYearsComponent = DaggerConventionYearsComponent.builder()
                .conventionYearsModule(new ConventionYearsModule())
                .build();
        mPhotoshootsComponent = DaggerPhotoshootsComponent.builder()
                .photoshootsModule(new PhotoshootsModule())
                .build();
        mSuggestionsComponent = DaggerSuggestionsComponent.builder()
                .suggestionModule(new SuggestionModule())
                .build();
    }

    public ConventionsComponent getConventionsComponent() {
        return mConventionsComponent;
    }

    public ConventionYearsComponent getConventionYearsComponent() {
        return mConventionYearsComponent;
    }

    public PhotoshootsComponent getPhotoshootsComponent() {
        return mPhotoshootsComponent;
    }

    public SuggestionsComponent getSuggestionsComponent() {
        return mSuggestionsComponent;
    }
}
