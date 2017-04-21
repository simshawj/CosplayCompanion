package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ListPhotoshootsModule;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component for injecting the ListPhothoshootsPresenter.
 *
 * @author James Simshaw
 */

@Singleton
@Component(modules = {ListPhotoshootsModule.class, CosplayCompanionAPIModule.class})
public interface ListPhotoshootsComponent {
    void inject(ShowConventionYearFragment fragment);
}
