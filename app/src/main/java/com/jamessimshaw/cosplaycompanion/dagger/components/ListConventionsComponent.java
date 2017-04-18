package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.dagger.modules.ListConventionsModule;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Interface for Dagger ListConventionsComponent for injecting the ListConventionsPresenter.
 *
 * @author James Simshaw
 */
@Singleton
@Component(modules = {ListConventionsModule.class, CosplayCompanionAPIModule.class})
public interface ListConventionsComponent {
    void inject(ListConventionsFragment fragment);
}
