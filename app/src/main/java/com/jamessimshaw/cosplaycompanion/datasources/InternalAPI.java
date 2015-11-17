package com.jamessimshaw.cosplaycompanion.datasources;

import com.jamessimshaw.cosplaycompanion.models.Convention;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by james on 11/15/15.
 */
public interface InternalAPI {
    @GET("conventions.json")
    Call<List<Convention>> getConventions();

}
