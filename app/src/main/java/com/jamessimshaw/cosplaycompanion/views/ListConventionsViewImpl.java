package com.jamessimshaw.cosplaycompanion.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.models.Convention;

import java.util.List;

/**
 * Created by james on 3/25/16.
 */
public class ListConventionsViewImpl extends View implements ListConventionsView {

    private Context mContext;

    public ListConventionsViewImpl(Context context) {
        super(context);
        init(context);
    }

    public ListConventionsViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListConventionsViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void addConventions(List<Convention> conventions) {

    }

    @Override
    public void displayWarning(String warning) {
        Toast.makeText(mContext, warning, Toast.LENGTH_LONG).show();
    }

    @Override
    public void done() {

    }
}
