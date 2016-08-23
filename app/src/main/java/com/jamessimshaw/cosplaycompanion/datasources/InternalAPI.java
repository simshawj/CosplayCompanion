package com.jamessimshaw.cosplaycompanion.datasources;

import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;
import com.jamessimshaw.cosplaycompanion.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by james on 11/15/15.
 */
public interface InternalAPI {
    // Registration
    @FormUrlEncoded
    @POST("auth")
    Call<User> register(@Field("email") String email, @Field("password") String password,
                        @Field("password_confirmation") String passwordVerify,
                        @Field("username") String username);

    // Convention Calls
    @GET("conventions.json")
    Call<List<Convention>> getConventions();

    @POST("conventions.json")
    Call<Convention> createConvention(@Body Convention convention);

    @PATCH("conventions/{id}.json")
    Call<Convention> updateConvention(@Path("id") long id, @Body Convention convention);

    // Convention Year Calls
    @GET("conventions/{id}/convention_years.json")
    Call<List<ConventionYear>> getConventionYears(@Path("id") long id);

    @POST("conventions/{id}/convention_years.json")
    Call<ConventionYear> createConventionYear(@Path("id") long id, @Body ConventionYear conventionYear);

    @PATCH("convention_years/{id}.json")
    Call<ConventionYear> updateConventionYear(@Path("id") long id, @Body ConventionYear conventionYear);

    // Photoshoot Calls
    @GET("convention_years/{id}/photo_shoots.json")
    Call<List<Photoshoot>> getPhotoShoots(@Path("id") long id);

    @POST("convention_years/{id}/photo_shoots.json")
    Call<Photoshoot> createPhotoShoot(@Path("id") long id, @Body Photoshoot photoshoot);

    @PATCH("photo_shoots/{id}.json")
    Call<Photoshoot> updatePhotoShoot(@Path("id") long id, @Body Photoshoot photoshoot);
}
