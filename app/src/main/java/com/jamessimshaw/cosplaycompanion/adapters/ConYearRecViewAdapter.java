package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by james on 10/11/15.
 */
public class ConYearRecViewAdapter extends RecyclerView.Adapter<ConYearRecViewAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_YEAR = 1;

    private Convention mConvention;
    private ArrayList<ConventionYear> mConventionYears;
    private Activity mActivity;

    public ConYearRecViewAdapter(Convention convention, ArrayList<ConventionYear> conventionYears,
                                 Activity activity) {
        mConvention = convention;
        mConventionYears = conventionYears;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (viewType == TYPE_HEADER) {
            layout = R.layout.row_convention;
        } else {
            layout = R.layout.row_convention_year;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ViewHolder(view, viewType, mActivity);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.mType == TYPE_HEADER) {
            holder.mConventionNameTextView.setText(mConvention.getName());
            holder.mConventionLogoImageView.setImageBitmap(mConvention.getLogo());
        } else {
            ConventionYear conventionYear = mConventionYears.get(position - 1);
            holder.mConventionYearYear.setText(conventionYear.getYearAsString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
            String dateString = dateFormat.format(conventionYear.getStartDate()) + " to " +
                    dateFormat.format(conventionYear.getEndDate());
            holder.mConventionYearDates.setText(dateString);
            holder.mConventionYear = conventionYear;
            holder.mConvention = mConvention;
        }

    }

    @Override
    public int getItemCount() {
        return mConventionYears.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_YEAR;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int mType;
        private TextView mConventionNameTextView;
        private ImageView mConventionLogoImageView;
        private TextView mConventionYearYear;
        private TextView mConventionYearDates;
        private Activity mActivity;
        private ConventionYear mConventionYear;
        private Convention mConvention;

        public ViewHolder(View itemView, int itemType, Activity activity) {
            super(itemView);

            mActivity = activity;
            mType = itemType;
            if (mType == TYPE_HEADER) {
                mConventionNameTextView = (TextView) itemView.findViewById(R.id.convention_name);
                mConventionLogoImageView = (ImageView) itemView.findViewById(R.id.convention_logo);
            } else {
                mConventionYearYear = (TextView) itemView.findViewById(R.id.convention_year);
                mConventionYearDates = (TextView) itemView.findViewById(R.id.convention_dates);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (mActivity instanceof MainActivity)
                ((MainActivity)mActivity).switchToConventionYearFragment(mConvention, mConventionYear);
        }
    }
}