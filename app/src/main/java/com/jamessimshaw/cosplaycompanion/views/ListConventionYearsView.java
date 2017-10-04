package com.jamessimshaw.cosplaycompanion.views;

/**
 * Created by james on 2/23/16.
 */
public interface ListConventionYearsView extends MVPView {

    void updateConventionLogo(String url);
    void updateConventionName(String name);
    void updateConventionDescription(String description);
    void setTitle(String title);
}
