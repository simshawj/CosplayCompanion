package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionYearsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface for Dagger ConventionYearsComponent for injecting the ListConventionYearsPresenter.
 *
 * @author James Simshaw
 */
@Singleton
@Component(modules = {ConventionYearsModule.class, CosplayCompanionAPIModule.class})
public interface ConventionYearsComponent {
    void inject(ShowConventionFragment fragment);
    void inject(ModifyConventionYearFragment fragment);
}
