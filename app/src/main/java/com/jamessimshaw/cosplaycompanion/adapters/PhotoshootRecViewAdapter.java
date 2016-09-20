package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/16/15.
 */
public class PhotoshootRecViewAdapter extends RecyclerView.Adapter<PhotoshootRecViewAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_PHOTOSHOOT = 1;

    private ArrayList<Photoshoot> mPhotoshoots;
    private ConventionYear mConventionYear;
    private Activity mActivity;

    public PhotoshootRecViewAdapter(ConventionYear conventionYear,
                                    ArrayList<Photoshoot> photoshoots, Activity activity) {
        mConventionYear = conventionYear;
        mPhotoshoots = photoshoots;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (viewType == TYPE_HEADER)
            layout = R.layout.row_convention_year;
        else
            layout = R.layout.row_photoshoot;

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder.mType == TYPE_HEADER) {
            holder.mConventionYearYear.setText(mConventionYear.getYearAsString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd", Locale.getDefault());
            String dateString = dateFormat.format(mConventionYear.getStartDate()) + " to " +
                    dateFormat.format(mConventionYear.getEndDate());
            holder.mConventionYearDates.setText(dateString);
        } else {
            final Photoshoot photoshoot = mPhotoshoots.get(position - 1);
            holder.mPhotoshootSeries.setText(photoshoot.getSeries());
            holder.mPhotoshootDescription.setText(photoshoot.getDescription());
            holder.mPhotoshootLocation.setText(photoshoot.getLocation());
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd yyyy @ hh:mm aa",
                    Locale.getDefault());
            holder.mPhotoshootDate.setText(dateFormat.format(photoshoot.getStart()));
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mActivity instanceof MainActivity)
                        ((MainActivity) mActivity).onFragmentInteraction("edit photoshoot", photoshoot);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_PHOTOSHOOT;
    }

    public void addConventionYears(List<Photoshoot> photoshoots) {
        mPhotoshoots.addAll(photoshoots);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPhotoshoots.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int mType;

        @Nullable @BindView(R.id.convention_year) TextView mConventionYearYear;
        @Nullable @BindView(R.id.convention_dates) TextView mConventionYearDates;
        @Nullable @BindView(R.id.seriesTextView) TextView mPhotoshootSeries;
        @Nullable @BindView(R.id.dateTextView) TextView mPhotoshootDate;
        @Nullable @BindView(R.id.locationTextView) TextView mPhotoshootLocation;
        @Nullable @BindView(R.id.descriptionTextView) TextView mPhotoshootDescription;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            mType = viewType;

            ButterKnife.bind(this, itemView);
        }
    }
}
