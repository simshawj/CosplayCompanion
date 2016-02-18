package com.jamessimshaw.cosplaycompanion.dagger.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.serialization.ConventionDeserializer;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    String mBaseURL;

    public NetworkModule(String baseURL) {
        mBaseURL = baseURL;
    }

    @Provides
    @Singleton
    @Named("conventions")
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Convention.class, new ConventionDeserializer())
                .create();
    }


    @Provides
    @Singleton
    @Named("conventions")
    Retrofit provideRetrofit(@Named("conventions") Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
