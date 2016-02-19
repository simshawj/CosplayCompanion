package com.jamessimshaw.cosplaycompanion.views;

import android.graphics.Bitmap;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionView {
    String getName();
    String getDescription();
    Bitmap getLogo();  //TODO: Is it possible to use a non-Android class here?
    void displayWarning(String message);
    void done();
}
