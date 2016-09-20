package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.annotation.Nullable;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.mType == TYPE_HEADER) {
            holder.mConventionNameTextView.setText(mConvention.getName());
            Picasso.with(mActivity)
                    .load(mConvention.getLogoUri())
                    .into(holder.mConventionLogoImageView);
            holder.mConventionDescriptionTextView.setText(mConvention.getDescription());
        } else {
            final ConventionYear conventionYear = mConventionYears.get(position - 1);
            holder.mConventionYearYear.setText(conventionYear.getYearAsString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
            String dateString = dateFormat.format(conventionYear.getStartDate()) + " to " +
                    dateFormat.format(conventionYear.getEndDate());
            holder.mConventionYearDates.setText(dateString);
            holder.mPhotoshootLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mActivity instanceof MainActivity)
                        ((MainActivity) mActivity).onFragmentInteraction("show conventionYear", conventionYear);
                }
            });
            holder.mEditTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (mActivity instanceof MainActivity)
                       ((MainActivity) mActivity).onFragmentInteraction("edit conventionYear", conventionYear);
                }
            });
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

    public void addConventionYears(List<ConventionYear> conventionYears) {
        mConventionYears.addAll(conventionYears);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private int mType;

        @Nullable @BindView(R.id.convention_name) TextView mConventionNameTextView;
        @Nullable @BindView(R.id.conDescriptionTextView) TextView mConventionDescriptionTextView;
        @Nullable @BindView(R.id.convention_logo) ImageView mConventionLogoImageView;
        @Nullable @BindView(R.id.convention_year) TextView mConventionYearYear;
        @Nullable @BindView(R.id.convention_dates) TextView mConventionYearDates;
        @Nullable @BindView(R.id.photoshoots) TextView mPhotoshootLink;
        @Nullable @BindView(R.id.yearEditText) TextView mEditTextView;

        public ViewHolder(View itemView, int itemType) {
            super(itemView);

            mType = itemType;

            ButterKnife.bind(this, itemView);
        }
    }
}
