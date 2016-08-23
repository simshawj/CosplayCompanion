package com.jamessimshaw.cosplaycompanion.views;

/**
 * Created by james on 8/19/16.
 */
public interface RegisterView extends MVPView {
    String getEmail();
    String getUsername();
    String getPassword();
    String getPasswordVerification();
}
