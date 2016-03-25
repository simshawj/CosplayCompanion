package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ListConventionsView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 2/21/16.
 */
public class ListConventionsPresenterImpl implements ListConventionsPresenter {

    @Inject @Named("conventions") Retrofit mRetrofit;

    private ListConventionsView mView;

    public ListConventionsPresenterImpl(ListConventionsView view) {
        mView = view;
        DaggerNetworkComponent.builder()
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule())
                .build().inject(this);
    }

    @Override
    public void requestConventions() {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        internalAPI.getConventions().enqueue(new Callback<List<Convention>>() {
            @Override
            public void onResponse(Call<List<Convention>> call, Response<List<Convention>> response) {
                mView.addConventions(response.body());
            }

            @Override
            public void onFailure(Call<List<Convention>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void requestNewConventions() {

    }

    @Override
    public void setView(ListConventionsView view) {
        mView = view;
    }

    @Override
    public void removeView(ListConventionsView view) {
        if(mView.equals(view))
            mView = null;
    }
}
