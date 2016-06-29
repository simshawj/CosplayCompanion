package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionYearView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 2/18/16.
 */
public class ModifyConventionYearPresenterImpl implements ModifyConventionYearPresenter {
    @Inject Retrofit mRetrofit;

    private ModifyConventionYearView mView;
    private Convention mConvention;
    private ConventionYear mConventionYear;
    private SimpleDateFormat mDateFormat;
    private Date mStartDate;
    private Date mEndDate;

    public ModifyConventionYearPresenterImpl(ModifyConventionYearView view, Convention convention, ConventionYear conventionYear) {
        mView = view;
        mConvention = convention;
        mConventionYear = conventionYear;

        DaggerNetworkComponent.builder()
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule())
                .build().inject(this);
    }

    @Override
    public void setView(ModifyConventionYearView view) {
        mView = view;
    }

    @Override
    public void setConvention(Convention convention) {
        mConvention = convention;
    }

    @Override
    public void setConventionYear(ConventionYear conventionYear) {
        mConventionYear = conventionYear;
    }

    @Override
    public void removeView(ModifyConventionYearView view) {
        if (mView.equals(view)) {
            mView = null;
            mConvention = null;
            mConventionYear = null;
        }
    }

    @Override
    public void requestInitialData() {
        mDateFormat = new SimpleDateFormat("EEEE MMMM dd yyyy", Locale.getDefault());
        if (mConventionYear == null) {
            Calendar calendar = Calendar.getInstance();
            mStartDate = calendar.getTime();
            mEndDate = calendar.getTime();
        } else {
            mStartDate = mConventionYear.getStartDate();
            mEndDate = mConventionYear.getEndDate();
            mView.displayLocation(mConventionYear.getLocation());
        }

        mView.displayStartDate(mDateFormat.format(mStartDate));
        mView.displayFinishDate(mDateFormat.format(mEndDate));
    }

    @Override
    public void setStartDate(Date date) {
        mStartDate = date;
        mView.displayStartDate(mDateFormat.format(mStartDate));
    }

    @Override
    public void setFinishDate(Date date) {
        mEndDate = date;
        mView.displayFinishDate(mDateFormat.format(mEndDate));
    }

    @Override
    public void submit() {
        String location = mView.getLocation();

        if (mStartDate.after(mEndDate)) {
            mView.displayWarning("End date must be after the start date");
            return;
        }

        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);

        String displayName = mConvention.getName() + " " + getYearFromDate(mStartDate);
        if (mConventionYear == null) {
            ConventionYear conventionYear = new ConventionYear(mStartDate, mEndDate,
                    mConvention.getId(), location, displayName);
            internalAPI.createConventionYear(mConvention.getId(), conventionYear).enqueue(mCallback);
        } else {
            mConventionYear.setStart(mStartDate);
            mConventionYear.setEnd(mEndDate);
            mConventionYear.setLocation(location);
            internalAPI.updateConventionYear(mConventionYear.getId(), mConventionYear).enqueue(mCallback);
        }
    }

    private String getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return Integer.toString(calendar.get(Calendar.YEAR));
    }

    Callback<ConventionYear> mCallback = new Callback<ConventionYear>() {
        @Override
        public void onResponse(Call<ConventionYear> call, Response<ConventionYear> response) {
            if (response.code() == 200 || response.code() == 201)
                mView.done();
            else {
                mView.displayWarning("Failed to create convention year.");
            }
        }

        @Override
        public void onFailure(Call<ConventionYear> call, Throwable t) {
            mView.displayWarning("Failed to create convention year, please check your connection and try again.");
        }
    };
}
