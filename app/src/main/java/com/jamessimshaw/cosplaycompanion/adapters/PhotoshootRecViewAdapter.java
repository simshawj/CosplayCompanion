package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/16/15.
 */
public class PhotoshootRecViewAdapter extends FirebaseRecyclerAdapter<Photoshoot, PhotoshootRecViewAdapter.ViewHolder> {
    private Activity mActivity;

    public PhotoshootRecViewAdapter(Class<Photoshoot> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, DatabaseReference reference, Activity activity) {
        super(modelClass, modelLayout, viewHolderClass, reference);
        mActivity = activity;
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, final Photoshoot photoshoot, int position) {
        viewHolder.mPhotoshootSeries.setText(photoshoot.getSeries());
        viewHolder.mPhotoshootDescription.setText(photoshoot.getDescription());
        viewHolder.mPhotoshootLocation.setText(photoshoot.getLocation());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd yyyy @ hh:mm aa",
                Locale.getDefault());
        viewHolder.mPhotoshootDate.setText(dateFormat.format(photoshoot.getStart()));
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mActivity instanceof MainActivity)
                    ((MainActivity) mActivity).onFragmentInteraction("edit photoshoot", photoshoot);
                return true;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.seriesTextView) TextView mPhotoshootSeries;
        @BindView(R.id.dateTextView) TextView mPhotoshootDate;
        @BindView(R.id.locationTextView) TextView mPhotoshootLocation;
        @BindView(R.id.descriptionTextView) TextView mPhotoshootDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
