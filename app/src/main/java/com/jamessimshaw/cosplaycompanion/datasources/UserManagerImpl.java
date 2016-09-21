package com.jamessimshaw.cosplaycompanion.datasources;

import com.jamessimshaw.cosplaycompanion.models.User;

/**
 * Created by james on 9/20/16.
 */
public class UserManagerImpl implements UserManager {
    private User mUser;

    @Override
    public void setUser(User user) {
        mUser = user;
    }

    @Override
    public User retrieveUser() {
        return mUser;
    }

    @Override
    public void removeUser() {
        mUser = null;
    }
}
