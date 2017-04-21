package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ListConventionYearsModule;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface for Dagger ListConventionYearsComponent for injecting the ListConventionYearsPresenter.
 *
 * @author James Simshaw
 */
@Singleton
@Component(modules = {ListConventionYearsModule.class, CosplayCompanionAPIModule.class})
public interface ListConventionYearsComponent {
    void inject(ShowConventionFragment fragment);
}
