package com.jamessimshaw.cosplaycompanion.controllers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.helpers.KeyboardHelper;
import com.jamessimshaw.cosplaycompanion.presenters.SuggestionPresenter;
import com.jamessimshaw.cosplaycompanion.views.SuggestionView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateSuggestionController extends BaseLandingController implements SuggestionView {

    @BindView(R.id.suggestionEditText) EditText mSuggestionEditText;
    @Inject SuggestionPresenter mPresenter;

    public static CreateSuggestionController newInstance() {
        return new CreateSuggestionController();
    }

    public CreateSuggestionController() {

    }

    @NonNull
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_landing_base, container, false);

        ViewStub stub = view.findViewById(R.id.contentHolder);
        stub.setLayoutResource(R.layout.fragment_new_feedback);
        stub.inflate();

        ((CosplayCompanionApplication)getActivity().getApplication()).getSuggestionsComponent()
                .inject(this);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback and Suggestions");
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_item_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        mPresenter.setView(this);
        setTitle("New Suggestion");
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mPresenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_submit) {
            mPresenter.submit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        KeyboardHelper.hideKeyboard(getActivity());
        getRouter().popCurrentController();
    }

    @Override
    public String getText() {
        return mSuggestionEditText.getText().toString();
    }

}
