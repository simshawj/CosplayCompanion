package com.jamessimshaw.cosplaycompanion.models;

import com.google.gson.annotations.SerializedName;

/**
 * Suggestion model
 *
 * @author James Simshaw
 */

public class Suggestion {

    @SerializedName("text")
    String mText;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Suggestion(String text) {
        mText = text;
    }
}
