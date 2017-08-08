package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ListPhotoshootsView;

import retrofit2.Retrofit;

/**
 * Created by james on 7/23/16.
 */
public class ListPhotoshootsPresenterImpl implements ListPhotoshootsPresenter {


    private Retrofit mRetrofit;

    private ListPhotoshootsView mView;
    private ConventionYear mConventionYear;

    public ListPhotoshootsPresenterImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public void requestPhotoshoots() {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
//        internalAPI.getPhotoShoots(mConventionYear.getId()).enqueue(new Callback<List<Photoshoot>>() {
//            @Override
//            public void onResponse(Call<List<Photoshoot>> call, Response<List<Photoshoot>> response) {
//                mView.addConventionYears(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Photoshoot>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

    @Override
    public void requestNewPhotoshoots() {

    }

    @Override
    public void setConventionYear(ConventionYear conventionYear) {
        mConventionYear = conventionYear;
    }

    @Override
    public void setView(ListPhotoshootsView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
