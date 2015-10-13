package com.jamessimshaw.cosplaycompanion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by james on 10/11/15.
 */
public class ConYearRecViewAdapter extends RecyclerView.Adapter<ConYearRecViewAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_YEAR = 1;

    private Convention mConvention;
    private ArrayList<ConventionYear> mConventionYears;

    public ConYearRecViewAdapter(Convention convention, ArrayList<ConventionYear> conventionYears) {
        mConvention = convention;
        mConventionYears = conventionYears;
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
            holder.mConventionLogoImageView.setImageBitmap(mConvention.getLogo());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mConventionYears.get(position - 1).getDate());
            holder.mConventionYearYear.setText(Integer.toString(calendar.get(Calendar.YEAR)));
            calendar.add(Calendar.DAY_OF_MONTH, mConventionYears.get(position - 1).getDays());
            Date endDate = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
            String dateString = dateFormat.format(mConventionYears.get(position - 1).getDate()) + " to " +
                    dateFormat.format(endDate);
            holder.mConventionYearDates.setText(dateString);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private int mType;
        private TextView mConventionNameTextView;
        private ImageView mConventionLogoImageView;
        private TextView mConventionYearYear;
        private TextView mConventionYearDates;

        public ViewHolder(View itemView, int itemType) {
            super(itemView);

            mType = itemType;
            if (mType == TYPE_HEADER) {
                mConventionNameTextView = (TextView) itemView.findViewById(R.id.convention_name);
                mConventionLogoImageView = (ImageView) itemView.findViewById(R.id.convention_logo);
            } else {
                mConventionYearYear = (TextView) itemView.findViewById(R.id.convention_year);
                mConventionYearDates = (TextView) itemView.findViewById(R.id.convention_dates);
            }
        }
    }
}
