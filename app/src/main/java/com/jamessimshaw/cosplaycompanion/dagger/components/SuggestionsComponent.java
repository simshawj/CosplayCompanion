package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.controllers.CreateSuggestionController;
import com.jamessimshaw.cosplaycompanion.dagger.modules.SuggestionModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component for Suggestions.
 *
 * @author James Simshaw
 */

@Singleton
@Component(modules = {SuggestionModule.class})
public interface SuggestionsComponent {
    void inject(CreateSuggestionController fragment);
}
