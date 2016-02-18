package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.NetworkModule;
import com.jamessimshaw.cosplaycompanion.fragments.ListConventionsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(ListConventionsFragment fragment);
}
