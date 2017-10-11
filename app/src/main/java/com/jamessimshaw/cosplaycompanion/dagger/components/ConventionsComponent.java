package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.controllers.ListConventionsController;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionsModule;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionDialogFragment;

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
    void inject(ModifyConventionDialogFragment fragment);
}
