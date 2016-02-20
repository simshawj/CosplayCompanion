package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.NetworkModule;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(ListConventionsFragment fragment);
    void inject(ModifyConventionPresenterImpl presenter);
    void inject(ModifyConventionYearPresenterImpl presenter);
    void inject(ModifyPhotoshootPresenterImpl presenter);
    void inject(ShowConventionFragment fragment);
    void inject(ShowConventionYearFragment fragment);
}
