package com.jamessimshaw.cosplaycompanion.views;

/**
 * Created by james on 2/19/16.
 */
public interface ModifyPhotoshootView extends MVPView {
    void updateDate(String date);
    void updateTime(String time);
    String getLocation();
    String getSeries();
    String getDescription();
    void displayLocation(String location);
    void displaySeries(String series);
    void displayDescription(String description);
}
