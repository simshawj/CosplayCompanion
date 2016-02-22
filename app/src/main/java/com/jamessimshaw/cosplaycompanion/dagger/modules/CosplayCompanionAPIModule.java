package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jamessimshaw.cosplaycompanion.BuildConfig;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.serialization.ConventionDeserializer;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CosplayCompanionAPIModule {

    String mBaseURL;

    public CosplayCompanionAPIModule() {
        mBaseURL = BuildConfig.CosplayCompanionAPIBase;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    @Named("conventions")
    Gson provideConventionGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Convention.class, new ConventionDeserializer())
                .create();
    }


    @Provides
    @Singleton
    @Named("conventions")
    Retrofit provideConventionRetrofit(@Named("conventions") Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
