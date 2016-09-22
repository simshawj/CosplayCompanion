package com.jamessimshaw.cosplaycompanion.datasources;

import android.content.SharedPreferences;

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerPreferenceComponent;
import com.jamessimshaw.cosplaycompanion.models.User;

import javax.inject.Inject;

/**
 * Created by james on 9/20/16.
 */
public class UserManagerImpl implements UserManager {
    @Inject SharedPreferences mPreferences;

    public UserManagerImpl() {
        CosplayCompanionApplication.getPreferenceComponent().inject(this);
    }

    @Override
    public void setUser(User user) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("user_uid", user.getUid());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_username", user.getUsername());
        editor.putInt("user_id", user.getId());
        editor.commit();
    }

    @Override
    public User retrieveUser() {
        String uid = mPreferences.getString("user_uid", "");
        String email = mPreferences.getString("user_email", "");
        String username = mPreferences.getString("user_username", "");
        int id = mPreferences.getInt("user_id", 0);
        return new User(id, email, username, uid);
    }
}
