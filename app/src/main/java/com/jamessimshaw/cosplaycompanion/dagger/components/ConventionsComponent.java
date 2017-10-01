package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.controllers.ListConventionsController;
import com.jamessimshaw.cosplaycompanion.controllers.ModifyConventionController;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface for Dagger ConventionsComponent for injecting the ListConventionsPresenter.
 *
 * @author James Simshaw
 */
@Singleton
@Component(modules = {ConventionsModule.class})
public interface ConventionsComponent {
    void inject(ListConventionsController fragment);
    void inject(ModifyConventionController fragment);
}
