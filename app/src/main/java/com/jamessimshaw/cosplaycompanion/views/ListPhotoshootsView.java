package com.jamessimshaw.cosplaycompanion.views;

import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.util.List;

/**
 * Created by james on 7/23/16.
 */
public interface ListPhotoshootsView extends MVPView {
    void addConventionYears(List<Photoshoot> photoshoots);
}
