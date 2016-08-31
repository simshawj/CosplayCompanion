package com.jamessimshaw.cosplaycompanion.datasources;

import com.jamessimshaw.cosplaycompanion.models.SessionToken;

/**
 * Created by james on 8/25/16.
 */
public interface TokenManager {
    SessionToken load();
    void save(SessionToken token);
}
