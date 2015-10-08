package com.jamessimshaw.cosplaycompanion.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;

/**
 * Created by james on 10/4/15.
 */
public class NewConventionFragment extends Fragment {

    OnFragmentInteractionListener mListener;
    EditText mNameEditText;
    EditText mDescriptionEditText;
    ImageView mLogoImageView;

    public static NewConventionFragment newInstance() {
        NewConventionFragment fragment = new NewConventionFragment();
        return fragment;
    }

    public NewConventionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_convention, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("New Convention");
        setHasOptionsMenu(true);

        mNameEditText = (EditText) view.findViewById(R.id.conventionNameEditText);
        mDescriptionEditText = (EditText) view.findViewById(R.id.descriptionEditText);
        mLogoImageView = (ImageView) view.findViewById(R.id.logoImageView);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_convention_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_submit) {
            String name = mNameEditText.getText().toString();
            String description = mDescriptionEditText.getText().toString();
            Bitmap logo = ((BitmapDrawable)mLogoImageView.getDrawable()).getBitmap();
            Convention convention = new Convention(name, description, logo);
            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource(getContext());
            sqLiteDataSource.create(convention);
            mListener.onNewConventionFragmentInteraction(convention);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onNewConventionFragmentInteraction(Convention convention);
    }
}
