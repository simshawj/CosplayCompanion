package com.jamessimshaw.cosplaycompanion.datasources;

import android.content.SharedPreferences;

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPreferenceComponent;
import com.jamessimshaw.cosplaycompanion.models.SessionToken;

import javax.inject.Inject;

/**
 * Created by james on 8/25/16.
 */
public class TokenManagerImpl implements TokenManager {
    @Inject SharedPreferences mSharedPreferences;

    public TokenManagerImpl() {
        CosplayCompanionApplication.getPreferenceComponent().inject(this);
    }

    // TokenManager methods

    @Override
    public SessionToken load() {
        SessionToken token = new SessionToken();
        token.setAccessToken(mSharedPreferences.getString("access-token", ""));
        token.setClient(mSharedPreferences.getString("client", ""));
        token.setUid(mSharedPreferences.getString("uid", ""));
        token.setExpiry(mSharedPreferences.getString("expiry", ""));
        return token;
    }

    @Override
    public void save(SessionToken token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("access-token", token.getAccessToken());
        editor.putString("client", token.getClient());
        editor.putString("uid", token.getUid());
        editor.putString("expiry", token.getExpiry());
        editor.commit();
    }
}
