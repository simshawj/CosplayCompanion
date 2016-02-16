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
import android.util.Base64;
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
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by james on 10/4/15.
 */
public class ModifyConventionFragment extends Fragment {
    public static final int LOGO = 0;

    @Bind(R.id.conventionNameEditText) EditText mNameEditText;
    @Bind(R.id.descriptionEditText) EditText mDescriptionEditText;
    @Bind(R.id.logoImageView) ImageView mLogoImageView;
    @Bind(R.id.conventionLogoChangeButton) Button mLogoButton;

    OnFragmentInteractionListener mListener;
    Uri mLogoUri;
    Convention mConvention;

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

        mConvention = null;

        if (getArguments() != null)
            mConvention = getArguments().getParcelable("convention");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_convention, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("New Convention");
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        mLogoButton.setOnClickListener(new View.OnClickListener() {
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
        });

        if (mConvention != null) {
            mNameEditText.setText(mConvention.getName());
            mDescriptionEditText.setText(mConvention.getDescription());
            mLogoUri = mConvention.getLogoUri();
            Picasso.with(getContext()).load(mLogoUri).into(mLogoImageView);
        }

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

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.internalAPIBase))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            InternalAPI internalAPI = retrofit.create(InternalAPI.class);

            Bitmap logo = ((BitmapDrawable)mLogoImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            logo.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            String logoString = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);

            if (mConvention == null) {
                Convention convention = new Convention(name, description, mLogoUri);
                convention.setBase64Logo(logoString);

                internalAPI.createConvention(convention).enqueue(new Callback<Convention>() {
                    @Override
                    public void onResponse(Call<Convention> call, Response<Convention> response) {
                        if (response.code() == 201)
                            mListener.onModifyFragmentInteraction();
                        else {
                            Toast.makeText(getContext(), "Failed to create convention.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Convention> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to create convention, please check your connection and try again.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                mConvention.setDescription(description);
                mConvention.setName(name);
                mConvention.setLogoUri(mLogoUri);
                internalAPI.updateConvention(mConvention.getId(), mConvention).enqueue(new Callback<Convention>() {
                    @Override
                    public void onResponse(Call<Convention> call, Response<Convention> response) {
                        if (response.code() == 200)
                            mListener.onModifyFragmentInteraction();
                        else {
                            Toast.makeText(getContext(), "Failed to update convention.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Convention> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to update convention, please check your connection and try again.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
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
        public void onModifyFragmentInteraction();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mLogoUri = data.getData();
            if (Build.VERSION.SDK_INT >= 19)
                getContext().getApplicationContext().getContentResolver().takePersistableUriPermission(
                        mLogoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
            Picasso.with(getContext()).load(mLogoUri).into(mLogoImageView);
        }
    }
}
