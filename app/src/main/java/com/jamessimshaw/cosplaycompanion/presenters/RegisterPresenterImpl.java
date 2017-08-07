package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.datasources.UserManager;
import com.jamessimshaw.cosplaycompanion.models.UserResponse;
import com.jamessimshaw.cosplaycompanion.views.RegisterView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Presenter for registering.
 *
 * @author James Simshaw
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private Retrofit mRetrofit;
    private UserManager mUserManager;

    RegisterView mView;

    @Inject
    public RegisterPresenterImpl(Retrofit retrofit, UserManager userManager) {
        mRetrofit = retrofit;
        mUserManager = userManager;
    }

    // RegisterPresenter methods

    @Override
    public void register() {
        if (mView == null)
            return;     // TODO: Implement better error routine

        if (!mView.getAgreementStatus()) {
            mView.displayWarning("You must accept the Terms of Use and Privacy Policy to register");
            return;
        }

        String email = mView.getEmail();
        String username = mView.getUsername();
        String password = mView.getPassword();
        String passwordVerify = mView.getPasswordVerification();
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        internalAPI.register(email, password, passwordVerify, username).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 201 || response.code() == 200) {
                    mUserManager.setUser(response.body().getUser());
                    mView.done();
                } else {
                    mView.displayWarning("Failed to register, but received result");
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mView.displayWarning("Failed to register");
            }
        });
    }

    @Override
    public void setView(RegisterView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
