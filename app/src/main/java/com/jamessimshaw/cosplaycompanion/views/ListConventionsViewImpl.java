package com.jamessimshaw.cosplaycompanion.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.jamessimshaw.cosplaycompanion.models.Convention;

import java.util.List;

/**
 * Created by james on 3/25/16.
 */
public class ListConventionsViewImpl extends RelativeLayout implements ListConventionsView {
    public ListConventionsViewImpl(Context context) {
        super(context);
    }

    public ListConventionsViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListConventionsViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public void addConventions(List<Convention> conventions) {

    }

    @Override
    public void displayWarning(String warning) {

    }

    @Override
    public void done() {

    }
}
