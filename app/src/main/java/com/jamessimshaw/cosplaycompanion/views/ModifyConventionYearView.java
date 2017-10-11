package com.jamessimshaw.cosplaycompanion.views;

/**
 * Created by james on 2/18/16.
 */
public interface ModifyConventionYearView extends MVPView {
    String getLocation();
    void displayStartDate(String date);
    void displayFinishDate(String date);
    void displayLocation(String location);

    void displayDisplayName(String displayName);

    String getDisplayName();
    void done();
}
