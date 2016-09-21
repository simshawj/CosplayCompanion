package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerLoginComponents;
import com.jamessimshaw.cosplaycompanion.dagger.components.LoginComponents;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.datasources.TokenManager;
import com.jamessimshaw.cosplaycompanion.datasources.TokenManagerImpl;
import com.jamessimshaw.cosplaycompanion.datasources.UserManager;
import com.jamessimshaw.cosplaycompanion.models.SessionToken;
import com.jamessimshaw.cosplaycompanion.models.User;
import com.jamessimshaw.cosplaycompanion.views.LoginView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 8/19/16.
 */
public class LoginPresenterImpl implements LoginPresenter {
    @Inject Retrofit mRetrofit;
    @Inject UserManager mUserManager;
    @Inject TokenManager mTokenManager;

    private LoginView mView;

    public LoginPresenterImpl() {
        DaggerLoginComponents.builder().build().inject(this);
    }

    // LoginPresenter methods

    @Override
    public void login() {
        if (mView == null)
            return;  // TODO: Implement better error checking

        String email = mView.getLoginName();
        String password = mView.getPassword();
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        internalAPI.sign_in(email, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    mUserManager.setUser(response.body());
                    mView.done();
                } else {
                    mView.displayWarning("Failed to login, but received result");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mView.displayWarning("Failed to login");
            }
        });
    }

    @Override
    public void verifyToken() {
        SessionToken token = mTokenManager.load();
        String uid = token.getUid();
        String client = token.getClient();
        String access = token.getAccessToken();
        if (!(uid.isEmpty() || client.isEmpty() || access.isEmpty())) {
            InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
            internalAPI.validate_token(uid, client, access).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200)
                        mView.done();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void setView(LoginView view) {
        mView = view;
    }

    @Override
    public void removeView(LoginView view) {
        if(mView.equals(view))
            mView = null;
    }
}
