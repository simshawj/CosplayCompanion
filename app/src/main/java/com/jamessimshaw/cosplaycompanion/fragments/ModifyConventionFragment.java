package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionPresenter;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionPresenterImpl;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/4/15.
 */
public class ModifyConventionFragment extends Fragment implements ModifyConventionView, View.OnClickListener {
    public static final int LOGO = 0;

    @BindView(R.id.conventionNameEditText) EditText mNameEditText;
    @BindView(R.id.descriptionEditText) EditText mDescriptionEditText;
    @BindView(R.id.logoImageView) ImageView mLogoImageView;
    @BindView(R.id.conventionLogoChangeButton) Button mLogoButton;

    ModifyConventionPresenter mPresenter;
    OnFragmentInteractionListener mListener;

    public static ModifyConventionFragment newInstance() {
        ModifyConventionFragment fragment = new ModifyConventionFragment();
        return fragment;
    }

    public static ModifyConventionFragment newInstance(Convention convention) {
        ModifyConventionFragment fragment = new ModifyConventionFragment();
        Bundle params = new Bundle();
        params.putParcelable("convention", convention);
        fragment.setArguments(params);
        return fragment;
    }

    public ModifyConventionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Convention convention = null;

        if (getArguments() != null)
            convention = getArguments().getParcelable("convention");

        mPresenter = new ModifyConventionPresenterImpl(this, convention);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_convention, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("New Convention");
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        mLogoButton.setOnClickListener(this);
        mPresenter.requestInitialData();

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPresenter.removeView(this);
        mPresenter = null;
    }

    public interface OnFragmentInteractionListener {
        void onModifyFragmentInteraction();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri logoUri = data.getData();
            if (Build.VERSION.SDK_INT >= 19)
                getContext().getApplicationContext().getContentResolver().takePersistableUriPermission(
                        logoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
            Picasso.with(getContext()).load(logoUri).into(mLogoImageView);
        }
    }

    // View.OnClickListener methods
    @Override
    public void onClick(View v) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19)
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        else
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, LOGO);
    }

    // ModifyConventionView methods

    @Override
    public String getName() {
        return mNameEditText.getText().toString();
    }

    @Override
    public String getDescription() {
        return mDescriptionEditText.getText().toString();
    }

    @Override
    public Bitmap getLogo() {
        return ((BitmapDrawable)mLogoImageView.getDrawable()).getBitmap();
    }

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayName(String name) {
        mNameEditText.setText(name);
    }

    @Override
    public void displayDescription(String description) {
        mDescriptionEditText.setText(description);
    }

    @Override
    public void displayLogo(Uri logoUri) {
        Picasso.with(getContext()).load(logoUri)
                .placeholder(android.R.drawable.ic_dialog_alert)
                .error(android.R.drawable.ic_dialog_alert)
                .into(mLogoImageView);
    }

    @Override
    public void done() {
        mListener.onModifyFragmentInteraction();
    }
}
