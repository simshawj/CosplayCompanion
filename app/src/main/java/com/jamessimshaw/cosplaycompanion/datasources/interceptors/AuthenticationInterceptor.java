package com.jamessimshaw.cosplaycompanion.datasources.interceptors;

import com.jamessimshaw.cosplaycompanion.dagger.components.DaggerTokenManagerComponent;
import com.jamessimshaw.cosplaycompanion.datasources.TokenManager;
import com.jamessimshaw.cosplaycompanion.models.SessionToken;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by james on 8/23/16.
 */

// TODO: May or may not go with this approach, more research is needed
public class AuthenticationInterceptor implements Interceptor {
    @Inject TokenManager mTokenManager;

    public AuthenticationInterceptor() {
        DaggerTokenManagerComponent.builder().build().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        SessionToken token = mTokenManager.load();

        // Create header for devise_token_auth gem
        request = request.newBuilder()
                .addHeader("access-token", token.getAccessToken())
                .addHeader("token-type", "Bearer")
                .addHeader("client", token.getClient())
                .addHeader("expiry", token.getExpiry())
                .addHeader("uid", token.getUid()).build();

        Response response = chain.proceed(request);

        SessionToken responseToken = new SessionToken();

        // If there are no new headers, use the old values
        responseToken.setAccessToken(response.header("access-token", token.getAccessToken()));
        responseToken.setClient(response.header("client", token.getClient()));
        responseToken.setExpiry(response.header("expiry", token.getExpiry()));
        responseToken.setUid(response.header("uid", token.getUid()));

        mTokenManager.save(responseToken);

        return response;
    }
}
