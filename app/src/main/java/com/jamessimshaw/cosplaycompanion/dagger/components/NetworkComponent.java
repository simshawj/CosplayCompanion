package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.NetworkModule;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyPhotoshootFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionFragment;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(ListConventionsFragment fragment);
    void inject(ModifyConventionFragment fragment);
    void inject(ModifyConventionYearFragment fragment);
    void inject(ModifyPhotoshootFragment fragment);
    void inject(ShowConventionFragment fragment);
    void inject(ShowConventionYearFragment fragment);
}
