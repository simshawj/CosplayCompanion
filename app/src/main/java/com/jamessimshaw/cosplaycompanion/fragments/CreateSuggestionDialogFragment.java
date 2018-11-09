package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.helpers.KeyboardHelper;
import com.jamessimshaw.cosplaycompanion.presenters.SuggestionPresenter;
import com.jamessimshaw.cosplaycompanion.views.SuggestionView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateSuggestionDialogFragment extends DialogFragment implements SuggestionView {

    @BindView(R.id.suggestionEditText) EditText mSuggestionEditText;
    @BindView(R.id.dialogSubmitTextView) TextView mSubmitTextView;
    @Inject SuggestionPresenter mPresenter;

    public static CreateSuggestionDialogFragment newInstance() {
        return new CreateSuggestionDialogFragment();
    }

    public CreateSuggestionDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_feedback, container, false);

        ((CosplayCompanionApplication)getActivity().getApplication()).getSuggestionsComponent()
                .inject(this);

        ButterKnife.bind(this, view);

        mSubmitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.submit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.detachView();
    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        KeyboardHelper.hideKeyboard(getActivity());
        dismiss();
    }

    @Override
    public String getText() {
        return mSuggestionEditText.getText().toString();
    }

}
