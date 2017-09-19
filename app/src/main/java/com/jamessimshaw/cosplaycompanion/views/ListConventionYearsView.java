package com.jamessimshaw.cosplaycompanion.views;

import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 2/23/16.
 */
public interface ListConventionYearsView extends MVPView {

    void updateData(List<ConventionYear> conventionYears);

    void updateConventionLogo(String url);
    void updateConventionName(String name);
    void updateConventionDescription(String description);
    void setTitle(String title);
}
