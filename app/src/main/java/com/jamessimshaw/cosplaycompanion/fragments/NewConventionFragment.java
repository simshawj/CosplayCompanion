package com.jamessimshaw.cosplaycompanion.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.jamessimshaw.cosplaycompanion.datasources.SQLiteDataSource;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.squareup.picasso.Picasso;

/**
 * Created by james on 10/4/15.
 */
public class NewConventionFragment extends Fragment {
    public static final int LOGO = 0;

    OnFragmentInteractionListener mListener;
    EditText mNameEditText;
    EditText mDescriptionEditText;
    ImageView mLogoImageView;
    Button mLogoButton;
    Uri mLogoUri;

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
        mLogoButton = (Button) view.findViewById(R.id.conventionLogoChangeButton);
        mLogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, LOGO);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mLogoUri = data.getData();
            Picasso.with(getContext()).load(mLogoUri).into(mLogoImageView);
        }
    }
}
