package com.jamessimshaw.cosplaycompanion.datasources;

import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Part;
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

    @GET("conventions/{id}/convention_years.json")
    Call<List<ConventionYear>> getConventionYears(@Path("id") long id);

    @POST("convention_years.json")
    Call<ConventionYear> createConventionYear(@Body ConventionYear conventionYear);

    @PATCH("convention_years/{id}.json")
    Call<ConventionYear> updateConventionYear(@Path("id") long id, @Body ConventionYear conventionYear);

    @GET("convention_years/{id}/photo_shoots.json")
    Call<List<Photoshoot>> getPhotoShoots(@Path("id") long id);

    @POST("photo_shoots.json")
    Call<Photoshoot> createPhotoShoot(@Body Photoshoot photoshoot);

    @PATCH("photo_shoots/{id}.json")
    Call<Photoshoot> updatePhotoShoot(@Path("id") long id, @Body Photoshoot photoshoot);
}
