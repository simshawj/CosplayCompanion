package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
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
import java.util.Locale;

/**
 * Created by james on 10/16/15.
 */
public class PhotoshootRecViewAdapter extends RecyclerView.Adapter<PhotoshootRecViewAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_PHOTOSHOOT = 1;

    private ArrayList<Photoshoot> mPhotoshoots;
    private Convention mConvention;
    private ConventionYear mConventionYear;
    private Activity mActivity;

    public PhotoshootRecViewAdapter(Convention convention, ConventionYear conventionYear,
                                    ArrayList<Photoshoot> photoshoots, Activity activity) {
        mConvention = convention;
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
            String dateString = dateFormat.format(mConventionYear.getStartDate()) + " to " +
                    dateFormat.format(mConventionYear.getEndDate());
            holder.mConventionYearDates.setText(dateString);
        } else {
            final Photoshoot photoshoot = mPhotoshoots.get(position - 1);
            holder.mPhotoshootSeries.setText(photoshoot.getSeries());
            holder.mPhotoshootDescription.setText(photoshoot.getDescription());
            holder.mPhotoshootLocation.setText(photoshoot.getLocation());
            SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd yyyy @ hh:mm aa",
                    Locale.getDefault());
            holder.mPhotoshootDate.setText(dateFormat.format(photoshoot.getStart()));
            holder.mEditPhotoshoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mActivity instanceof MainActivity)
                        ((MainActivity) mActivity).switchtoEditPhotoshoot(photoshoot);
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

    @Override
    public int getItemCount() {
        return mPhotoshoots.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int mType;
        private TextView mConventionYearYear;
        private TextView mConventionYearDates;
        private TextView mPhotoshootSeries;
        private TextView mPhotoshootDate;
        private TextView mPhotoshootLocation;
        private TextView mPhotoshootDescription;
        private TextView mEditPhotoshoot;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            mType = viewType;
            if (mType == TYPE_HEADER) {
                mConventionYearYear = (TextView) itemView.findViewById(R.id.convention_year);
                mConventionYearDates = (TextView) itemView.findViewById(R.id.convention_dates);
                itemView.findViewById(R.id.options).setVisibility(View.GONE);   //Removes options
                itemView.findViewById(R.id.horizontalRule).setVisibility(View.GONE);
            } else {
                mPhotoshootSeries = (TextView) itemView.findViewById(R.id.seriesTextView);
                mPhotoshootDate = (TextView) itemView.findViewById(R.id.dateTextView);
                mPhotoshootLocation = (TextView) itemView.findViewById(R.id.locationTextView);
                mPhotoshootDescription = (TextView) itemView.findViewById(R.id.descriptionTextView);
                mEditPhotoshoot = (TextView) itemView.findViewById(R.id.editPhotoshoot);
            }
        }
    }
}
