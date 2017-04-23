package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.ConventionsModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface for Dagger ConventionsComponent for injecting the ListConventionsPresenter.
 *
 * @author James Simshaw
 */
@Singleton
@Component(modules = {ConventionsModule.class, CosplayCompanionAPIModule.class})
public interface ConventionsComponent {
    void inject(ListConventionsFragment fragment);
    void inject(ModifyConventionFragment fragment);
}
