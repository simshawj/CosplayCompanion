package com.jamessimshaw.cosplaycompanion.models;

/**
 * Suggestion model
 *
 * @author James Simshaw
 */

public class Suggestion {

    private String mText;

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
