package com.jamessimshaw.cosplaycompanion.models;

import com.google.gson.annotations.SerializedName;

/**
 * Response from API when signing out.
 *
 * @author James Simshaw
 */

public class SignoutResponse {
    @SerializedName("success")
    private boolean mSuccess;
}
