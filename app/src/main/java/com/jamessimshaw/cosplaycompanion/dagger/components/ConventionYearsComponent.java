package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.controllers.ModifyConventionYearDialogFragment;
import com.jamessimshaw.cosplaycompanion.controllers.ShowConventionController;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionYearsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface for Dagger ConventionYearsComponent for injecting the ListConventionYearsPresenter.
 *
 * @author James Simshaw
 */
@Singleton
@Component(modules = {ConventionYearsModule.class})
public interface ConventionYearsComponent {
    void inject(ShowConventionController fragment);
    void inject(ModifyConventionYearDialogFragment fragment);
}
