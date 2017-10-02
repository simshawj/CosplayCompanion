package com.jamessimshaw.cosplaycompanion.controllers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jamessimshaw.cosplaycompanion.CosplayCompanionApplication;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.helpers.KeyboardHelper;
import com.jamessimshaw.cosplaycompanion.presenters.ModifyConventionPresenter;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/4/15.
 */
public class ModifyConventionController extends Controller implements ModifyConventionView, View.OnClickListener {
    public static final int LOGO = 0;

    @BindView(R.id.conventionNameEditText) EditText mNameEditText;
    @BindView(R.id.descriptionEditText) EditText mDescriptionEditText;
    @BindView(R.id.logoImageView) ImageView mLogoImageView;
    //@BindView(R.id.conventionLogoChangeButton) Button mLogoButton;

    @Inject ModifyConventionPresenter mPresenter;
//    private OnFragmentInteractionListener mListener;
    private Uri mLogoUri;

    public ModifyConventionController() {
//        super();
    }

    protected ModifyConventionController(@Nullable Bundle args) {
        super(args);
    }

    public static ModifyConventionController newInstance() {
        return new ModifyConventionController();
    }

    public static ModifyConventionController newInstance(String conventionRef) {
        Bundle params = new Bundle();
        params.putString("convention", conventionRef);
        return new ModifyConventionController(params);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_new_convention, container, false);

//        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        if (mPresenter.isEditMode()) {
//            actionBar.setTitle("Edit Convention");
//        } else {
//            actionBar.setTitle("New Convention");
//        }


        DatabaseReference convention = null;

        String reference = getArgs().getString("convention");
        if (reference != null) {
            convention = FirebaseDatabase.getInstance().getReferenceFromUrl(reference);
        } else {
            convention = null;
        }

        ((CosplayCompanionApplication)getActivity().getApplication()).getConventionsComponent().inject(this);

        mPresenter.setConvention(convention);

        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        mLogoImageView.setOnClickListener(this);
        ViewTreeObserver observer = mLogoImageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver observer = mLogoImageView.getViewTreeObserver();
                observer.removeOnGlobalLayoutListener(this);
                mPresenter.requestInitialData();
            }
        });

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mPresenter.setView(this);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_item_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_submit:
                mPresenter.submit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mLogoUri = data.getData();
            if (Build.VERSION.SDK_INT >= 19)
                getActivity().getApplicationContext().getContentResolver().takePersistableUriPermission(
                        mLogoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
            displayLogo(mLogoUri.toString());
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
    public InputStream getLogo() {
        try {
            return getActivity().getContentResolver().openInputStream(mLogoUri);
        } catch (IOException e) {
            return null;     // Should never happen since we just got the Uri, but send place
        } catch (NullPointerException e) {
            return null;     // Do not have an image, send placeholder
        }
    }

    @Override
    public void displayMessage(String warning) {
        Toast.makeText(getActivity(), warning, Toast.LENGTH_LONG).show();
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
    public void displayLogo(String logoUri) {
        Picasso.with(getActivity()).load(logoUri)
                .placeholder(android.R.drawable.ic_dialog_alert)
                .error(android.R.drawable.ic_dialog_alert)
                .resize(mLogoImageView.getWidth(),0)
                .into(mLogoImageView);
    }

    @Override
    public void done() {
        KeyboardHelper.hideKeyboard(getActivity());
//        mListener.onModifyFragmentInteraction();
    }
}
