package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.controllers.ModifyPhotoshootController;
import com.jamessimshaw.cosplaycompanion.controllers.ShowConventionYearController;
import com.jamessimshaw.cosplaycompanion.dagger.modules.PhotoshootsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component for injecting the ListPhothoshootsPresenter.
 *
 * @author James Simshaw
 */

@Singleton
@Component(modules = {PhotoshootsModule.class})
public interface PhotoshootsComponent {
    void inject(ShowConventionYearController fragment);
    void inject(ModifyPhotoshootController fragment);
}
