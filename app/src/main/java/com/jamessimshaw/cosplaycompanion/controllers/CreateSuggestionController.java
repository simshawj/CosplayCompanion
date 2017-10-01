package com.jamessimshaw.cosplaycompanion.controllers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.helpers.KeyboardHelper;
import com.jamessimshaw.cosplaycompanion.presenters.SuggestionPresenter;
import com.jamessimshaw.cosplaycompanion.views.SuggestionView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment for creating a suggestion.
 *
 * @author James Simshaw
 */

public class CreateSuggestionController extends Controller implements SuggestionView {

    @BindView(R.id.suggestionEditText) EditText mSuggestionEditText;

//    private OnFragmentInteractionListener mListener;
    @Inject SuggestionPresenter mPresenter;

    public static CreateSuggestionController newInstance() {
        return new CreateSuggestionController();
    }

    public CreateSuggestionController() {

    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        ((CosplayCompanionApplication)getActivity().getApplication()).getSuggestionsComponent()
//                .inject(this);
//    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_new_feedback, container, false);

        ((CosplayCompanionApplication)getActivity().getApplication()).getSuggestionsComponent()
                .inject(this);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback and Suggestions");
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mListener = (CreateSuggestionController.OnFragmentInteractionListener) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException(getActivity().toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_item_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

//    @Override
//    public void onPause() {
//        super.onPause();
//        mPresenter.detachView();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mPresenter.setView(this);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {
        KeyboardHelper.hideKeyboard(getActivity());
//        mListener.onModifyFragmentInteraction();
    }

    @Override
    public String getText() {
        return mSuggestionEditText.getText().toString();
    }

//    public interface OnFragmentInteractionListener {
//        void onModifyFragmentInteraction();
//    }
}
