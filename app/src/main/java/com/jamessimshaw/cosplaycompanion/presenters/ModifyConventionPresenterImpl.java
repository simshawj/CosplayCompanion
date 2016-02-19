package com.jamessimshaw.cosplaycompanion.presenters;

import android.graphics.Bitmap;
import android.util.Base64;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerNetworkComponent;
import com.jamessimshaw.cosplaycompanion.dagger.modules.NetworkModule;
import com.jamessimshaw.cosplaycompanion.datasources.InternalAPI;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.views.ModifyConventionView;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by james on 2/18/16.
 */
public class ModifyConventionPresenterImpl implements ModifyConventionPresenter {
    @Inject Retrofit mRetrofit;

    private Convention mConvention;
    private ModifyConventionView mView;

    public ModifyConventionPresenterImpl(ModifyConventionView view, Convention convention) {
        mView = view;
        mConvention = convention;

        //TODO: Eliminate hardcoded string
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://192.168.1.202:3000/"))
                .build().inject(this);
    }

    @Override
    public void setView(ModifyConventionView view) {
        mView = view;
    }

    @Override
    public void removeView(ModifyConventionView view) {
        if (mView.equals(view))
            mView = null;
    }

    @Override
    public void submit() {
        InternalAPI internalAPI = mRetrofit.create(InternalAPI.class);
        String name = mView.getName();
        String description = mView.getDescription();
        Bitmap logo = mView.getLogo();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        logo.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        String logoString = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP);

        if (mConvention == null) {
            Convention convention = new Convention(name, description, null);
            convention.setBase64Logo(logoString);
            internalAPI.createConvention(convention).enqueue(mConventionCallback);
        } else {
            mConvention.setDescription(description);
            mConvention.setName(name);
            //mConvention.setLogoUri(mLogoUri);
            internalAPI.updateConvention(mConvention.getId(), mConvention).enqueue(mConventionCallback);
        }
    }

    private Callback<Convention> mConventionCallback = new Callback<Convention>() {
        @Override
        public void onResponse(Call<Convention> call, Response<Convention> response) {
            if (response.code() == 200 || response.code() == 201)
                mView.done();
            else {
                mView.displayWarning("Failed to update convention.");
            }
        }

        @Override
        public void onFailure(Call<Convention> call, Throwable t) {
            mView.displayWarning("Failed to update convention, please check your connection and try again.");
        }
    };
}
