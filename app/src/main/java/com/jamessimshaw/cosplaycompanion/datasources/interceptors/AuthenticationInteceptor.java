package com.jamessimshaw.cosplaycompanion.datasources.interceptors;

import com.jamessimshaw.cosplaycompanion.models.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by james on 8/23/16.
 */

// TODO: May or may not go with this approach, more research is needed
public class AuthenticationInteceptor implements Interceptor {
    private User mUser;

    public AuthenticationInteceptor(User user) {
        mUser = user;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Create header for devise_token_auth gem
        request = request.newBuilder()
                .addHeader("access-token", mUser.getToken().getAccessToken())
                .addHeader("token-type", "Bearer")
                .addHeader("client", mUser.getToken().getClient())
                .addHeader("expiry", mUser.getToken().getExpiry())
                .addHeader("uid", mUser.getUid()).build();

        Response response = chain.proceed(request);

        return response;
    }
}
