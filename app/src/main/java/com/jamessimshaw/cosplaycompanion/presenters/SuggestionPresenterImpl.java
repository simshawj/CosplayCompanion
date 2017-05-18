package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.Suggestion;
import com.jamessimshaw.cosplaycompanion.views.SuggestionView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Implementation of the feedback presenter.
 *
 * @author James Simshaw
 */

public class SuggestionPresenterImpl implements SuggestionPresenter {

    private Retrofit mRetrofit;
    private SuggestionView mView;

    @Inject
    public SuggestionPresenterImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public void submit() {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        Suggestion suggestion = new Suggestion(mView.getText());
        internalAPI.createSuggestion(suggestion).enqueue(new Callback<Suggestion>() {
            @Override
            public void onResponse(Call<Suggestion> call, Response<Suggestion> response) {
                if (response.isSuccessful()) {
                    mView.done();
                } else {
                    mView.displayWarning("Failed to submit feedback.");
                }
            }

            @Override
            public void onFailure(Call<Suggestion> call, Throwable t) {
                mView.displayWarning("Failed to communicate with server.  Please check your connection.");
            }
        });
    }

    @Override
    public void setView(SuggestionView view) {
        mView = view;
    }

    @Override
    public void removeView(SuggestionView view) {
        if (mView.equals(view))
            mView = null;
    }
}
