package com.jamessimshaw.cosplaycompanion.views;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionView extends MVPView {
    String getName();
    String getDescription();
    Bitmap getLogo();  //TODO: Is it possible to use a non-Android class here?
    void displayName(String name);
    void displayDescription(String description);
    void displayLogo(Uri logoUri);
}