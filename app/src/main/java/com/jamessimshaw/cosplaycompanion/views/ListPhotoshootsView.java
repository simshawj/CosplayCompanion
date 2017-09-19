package com.jamessimshaw.cosplaycompanion.views;

/**
 * Created by james on 7/23/16.
 */
public interface ListPhotoshootsView extends MVPView {
    void updateTitle(String title);
    void updateDates(long start, long end);
    void setTitle(String title); // AppBar title
}
