package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.views.ModifyPhotoshootView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 2/19/16.
 */
public class ModifyPhotoshootPresenterImpl implements ModifyPhotoshootPresenter {
    private Retrofit mRetrofit;

    private ModifyPhotoshootView mView;
    private Photoshoot mPhotoshoot;
    private ConventionYear mConventionYear;
    private Calendar mStart;

    @Inject
    public ModifyPhotoshootPresenterImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    // ModifyPhotoshootPresenter methods

    @Override
    public void setView(ModifyPhotoshootView view) {
        mView = view;
    }

    @Override
    public void setConventionYear(ConventionYear conventionYear) {
        mConventionYear = conventionYear;
    }

    @Override
    public void setPhotoshoot(Photoshoot photoshoot) {
        mPhotoshoot = photoshoot;
    }

    @Override
    public void removeView(ModifyPhotoshootView view) {
        if (mView.equals(view))
            mView = null;
    }

    @Override
    public void requestInitialData() {
        mStart = Calendar.getInstance();
        if(mPhotoshoot != null) {
            mStart.setTime(mPhotoshoot.getStart());
            mView.displayLocation(mPhotoshoot.getLocation());
            mView.displaySeries(mPhotoshoot.getSeries());
            mView.displayDescription(mPhotoshoot.getDescription());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
        mView.updateDate(dateFormat.format(mStart.getTime()));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        mView.updateTime(timeFormat.format(mStart.getTime()));
    }

    @Override
    public void submit() {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        String series = mView.getSeries();
        String location = mView.getLocation();
        String description = mView.getDescription();
        if (mPhotoshoot == null) {
            Photoshoot photoshoot = new Photoshoot(series,
                    mStart.getTime(), location, description, mConventionYear.getId());
            internalAPI.createPhotoShoot(mConventionYear.getId(), photoshoot).enqueue(mCallback);
        } else {
            mPhotoshoot.setDescription(description);
            mPhotoshoot.setLocation(location);
            mPhotoshoot.setSeries(series);
            mPhotoshoot.setStart(mStart.getTime());
            internalAPI.updatePhotoShoot(mPhotoshoot.getId(), mPhotoshoot).enqueue(mCallback);
        }
    }

    @Override
    public void dateChanged(int year, int month, int day) {
        mStart.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
        mView.updateDate(dateFormat.format(mStart.getTime()));
    }

    @Override
    public void timeChanged(int hour, int minute) {
        mStart.set(Calendar.HOUR_OF_DAY, hour);
        mStart.set(Calendar.MINUTE, minute);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        mView.updateTime(timeFormat.format(mStart.getTime()));
    }

    // Callbacks

    private Callback<Photoshoot> mCallback = new Callback<Photoshoot>() {
        @Override
        public void onResponse(Call<Photoshoot> call, Response<Photoshoot> response) {
            if (response.code() == 200 || response.code() == 201)
                mView.done();
            else {
                mView.displayWarning("Failed to create photo shoot.");
            }
        }

        @Override
        public void onFailure(Call<Photoshoot> call, Throwable t) {
            mView.displayWarning("Failed to create photo shoot, please check your connection and try again.");
        }
    };
}
