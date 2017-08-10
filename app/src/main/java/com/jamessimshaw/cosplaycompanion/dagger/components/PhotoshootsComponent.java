package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.PhotoshootsModule;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyPhotoshootFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;

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
    void inject(ShowConventionYearFragment fragment);
    void inject(ModifyPhotoshootFragment fragment);
}
