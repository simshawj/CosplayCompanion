package com.jamessimshaw.cosplaycompanion.presenters;

import com.jamessimshaw.cosplaycompanion.views.SuggestionView;

/**
 * Presenter for handling feedback.
 *
 * @author James Simshaw
 */

public interface SuggestionPresenter extends Presenter<SuggestionView> {
    void submit();
}
