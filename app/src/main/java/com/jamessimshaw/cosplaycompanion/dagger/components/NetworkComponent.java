package com.jamessimshaw.cosplaycompanion.dagger.components;

import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.fragments.ShowConventionYearFragment;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionYearsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ListConventionsPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionYearPresenterImpl;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyPhotoshootPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CosplayCompanionAPIModule.class})
public interface NetworkComponent {
    void inject(ListConventionsPresenterImpl presenter);
    void inject(ModifyConventionPresenterImpl presenter);
    void inject(ModifyConventionYearPresenterImpl presenter);
    void inject(ModifyPhotoshootPresenterImpl presenter);
    void inject(ListConventionYearsPresenterImpl presenter);
    void inject(ShowConventionYearFragment fragment);
}
