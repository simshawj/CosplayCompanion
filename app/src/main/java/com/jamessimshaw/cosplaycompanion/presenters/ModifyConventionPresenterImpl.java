package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Prensenter for creating or editing a convention.
 *
 * @author James Simshaw
 */
public class ModifyConventionPresenterImpl implements ModifyConventionPresenter {
    private Retrofit mRetrofit;

    private Convention mConvention;
    private ModifyConventionView mView;

    @Inject
    public ModifyConventionPresenterImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    // ModifyConventionPresenter methods

    @Override
    public void setView(ModifyConventionView view) {
        mView = view;
    }

    @Override
    public void removeView(ModifyConventionView view) {
        if (mView.equals(view))
            mView = null;
    }

    @Override
    public void requestInitialData() {
        if (mConvention != null) {
            mView.displayName(mConvention.getName());
            mView.displayDescription(mConvention.getDescription());
            mView.displayLogo(mConvention.getLogoUri());
        }
    }

    @Override
    public void setConvention(Convention convention) {
        mConvention = convention;
    }

    @Override
    public void submit() {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        String name = mView.getName();
        String description = mView.getDescription();
        String logoString = mView.getLogo();


        if (mConvention == null) {
            Convention convention = new Convention(name, description, null);
            convention.setBase64Logo(logoString);
            internalAPI.createConvention(convention).enqueue(mConventionCallback);
        } else {
            mConvention.setDescription(description);
            mConvention.setName(name);
            mConvention.setBase64Logo(logoString);
            //mConvention.setLogoUri(mLogoUri);
            internalAPI.updateConvention(mConvention.getId(), mConvention).enqueue(mConventionCallback);
        }
    }

    // Callbacks

    private Callback<Convention> mConventionCallback = new Callback<Convention>() {
        @Override
        public void onResponse(Call<Convention> call, Response<Convention> response) {
            if (response.code() == 200 || response.code() == 201)
                mView.done();
            else {
                mView.displayWarning("Failed to update convention.");
            }
        }

        @Override
        public void onFailure(Call<Convention> call, Throwable t) {
            mView.displayWarning("Failed to update convention, please check your connection and try again.");
        }
    };
}
