package com.jamessimshaw.cosplaycompanion.datasources;

import com.jamessimshaw.cosplaycompanion.activities.SignedOut;
import com.jamessimshaw.cosplaycompanion.models.User;

/**
 * Created by james on 9/20/16.
 */
public interface UserManager {
    void setUser(User user);
    User retrieveUser();
    void sign_out(SignedOut signedOut);
}
