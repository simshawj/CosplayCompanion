package com.jamessimshaw.cosplaycompanion.presenters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.models.Suggestion;
import com.jamessimshaw.cosplaycompanion.views.SuggestionView;

import javax.inject.Inject;

/**
 * Implementation of the feedback presenter.
 *
 * @author James Simshaw
 */

public class SuggestionPresenterImpl implements SuggestionPresenter {

    private SuggestionView mView;
    private DatabaseReference mDatabaseReference;

    @Inject
    public SuggestionPresenterImpl() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("suggestions");
    }

    @Override
    public void submit() {
        Suggestion suggestion = new Suggestion(mView.getText());

        mDatabaseReference.push().setValue(suggestion);
        mView.done();
    }

    @Override
    public void setView(SuggestionView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
