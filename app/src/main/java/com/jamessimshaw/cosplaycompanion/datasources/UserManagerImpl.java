package com.jamessimshaw.cosplaycompanion.datasources;

import android.content.SharedPreferences;
import android.util.Log;

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.activities.SignedOut;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPreferenceComponent;
import com.jamessimshaw.cosplaycompanion.models.SignoutResponse;
import com.jamessimshaw.cosplaycompanion.models.User;
import com.jamessimshaw.cosplaycompanion.models.UserResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Helper class for handling Users
 *
 * @author James Simshaw
 */
public class UserManagerImpl implements UserManager {
    private SharedPreferences mPreferences;
    private Retrofit mRetrofit;

    @Inject
    public UserManagerImpl(Retrofit retrofit, SharedPreferences preferences) {
        mPreferences = preferences;
        mRetrofit = retrofit;
        CosplayCompanionApplication.getPreferenceComponent().inject(this);
    }

    @Override
    public void setUser(User user) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("user_uid", user.getUid());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_username", user.getUsername());
        editor.putInt("user_id", user.getId());
        editor.apply();
    }

    @Override
    public User retrieveUser() {
        String uid = mPreferences.getString("user_uid", "");
        String email = mPreferences.getString("user_email", "");
        String username = mPreferences.getString("user_username", "");
        int id = mPreferences.getInt("user_id", 0);
        return new User(id, email, username, uid);
    }

    @Override
    public void sign_out(final SignedOut caller) {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        internalAPI.sign_out().enqueue(new Callback<SignoutResponse>() {
            @Override
            public void onResponse(Call<SignoutResponse> call, Response<SignoutResponse> response) {
                if(response.isSuccessful()) {
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.remove("user_uid");
                    editor.remove("user_email");
                    editor.remove("user_username");
                    editor.remove("user_id");
                    editor.apply();
                    caller.signedOut();
                }
            }

            @Override
            public void onFailure(Call<SignoutResponse> call, Throwable t) {

            }
        });
    }


}
