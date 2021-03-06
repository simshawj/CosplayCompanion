package com.jamessimshaw.cosplaycompanion.views;

import java.io.InputStream;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionView extends MVPView {
    String getName();
    String getDescription();
    InputStream getLogo();
    void displayName(String name);
    void displayDescription(String description);
    void displayLogo(String logoUri);
    void done();
}
