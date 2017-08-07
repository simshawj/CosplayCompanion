package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ListConventionYearsView;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by james on 2/23/16.
 */
public class ListConventionYearsPresenterImpl implements ListConventionYearsPresenter {

    private Retrofit mRetrofit;

    private ListConventionYearsView mView;
    private Convention mConvention;

    @Inject
    public ListConventionYearsPresenterImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public void requestConventionYears() {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
//        internalAPI.getConventionYears(mConvention.getId()).enqueue(new Callback<List<ConventionYear>>() {
//            @Override
//            public void onResponse(Call<List<ConventionYear>> call, Response<List<ConventionYear>> response) {
//                mView.addConventionYears(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<ConventionYear>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

    @Override
    public void requestNewConventionYears() {

    }

    @Override
    public void setConvention(Convention convention) {
        mConvention = convention;
    }

    @Override
    public void setView(ListConventionYearsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
