package com.jamessimshaw.cosplaycompanion.views;

import com.jamessimshaw.cosplaycompanion.models.Convention;

import java.util.List;

/**
 * Created by james on 2/21/16.
 */
public interface ListConventionsView extends MVPView {
    void addConventions(List<Convention> conventions);
}
