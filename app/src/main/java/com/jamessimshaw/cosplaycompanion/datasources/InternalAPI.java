package com.jamessimshaw.cosplaycompanion.datasources;

import com.jamessimshaw.cosplaycompanion.models.Convention;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by james on 11/15/15.
 */
public interface InternalAPI {
    @GET("conventions.json")
    Call<List<Convention>> getConventions();

    @POST("conventions.json")
    Call<Convention> createConvention(@Body Convention convention);

    @PATCH("conventions/{id}.json")
    Call<Convention> updateConvention(@Path("id") long id, @Body Convention convention);
}
