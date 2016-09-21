package com.jamessimshaw.cosplaycompanion.presenters;

import android.util.Log;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerLoginComponents;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.CosplayCompanionAPIModule;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.datasources.UserManager;
import com.jamessimshaw.cosplaycompanion.models.User;
import com.jamessimshaw.cosplaycompanion.views.RegisterView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 8/19/16.
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    @Inject Retrofit mRetrofit;
    @Inject UserManager mUserManager;

    RegisterView mView;

    public RegisterPresenterImpl() {
        DaggerLoginComponents.builder()
                .cosplayCompanionAPIModule(new CosplayCompanionAPIModule())
                .build().inject(this);
    }

    // RegisterPresenter methods

    @Override
    public void register() {
        if (mView == null)
            return;     // TODO: Implement better error routine

        String email = mView.getEmail();
        String username = mView.getUsername();
        String password = mView.getPassword();
        String passwordVerify = mView.getPasswordVerification();
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        internalAPI.register(email, password, passwordVerify, username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201 || response.code() == 200) {
                    mUserManager.setUser(response.body());
                    mView.done();
                } else {
                    mView.displayWarning("Failed to register, but received result");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mView.displayWarning("Failed to register");
            }
        });
    }

    @Override
    public void setView(RegisterView view) {
        mView = view;
    }

    @Override
    public void removeView(RegisterView view) {
        if(mView.equals(view))
            mView = null;
    }
}
