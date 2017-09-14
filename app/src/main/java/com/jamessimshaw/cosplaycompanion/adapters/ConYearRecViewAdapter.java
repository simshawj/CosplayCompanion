package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/11/15.
 */
public class ConYearRecViewAdapter extends FirebaseIndexRecyclerAdapter<ConventionYear, ConYearRecViewAdapter.ViewHolder> {
    private Activity mActivity;

    public ConYearRecViewAdapter(Class<ConventionYear> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, Query keyQuery, DatabaseReference dataRef, Activity activity) {
        super(modelClass, modelLayout, viewHolderClass, keyQuery, dataRef);
        mActivity = activity;
    }
//    private List<ConventionYear> mConventionYears;

//    public ConYearRecViewAdapter(List<ConventionYear> conventionYears, Activity activity) {
//        mActivity = activity;
//        mConventionYears = conventionYears;
//    }

//    public ConYearRecViewAdapter(Class<ConventionYear> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, Query query, LifecycleOwner owner, Activity activity) {
//        super(modelClass, modelLayout, viewHolderClass, query, owner);
//        mActivity = activity;
//    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_convention_year, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        final ConventionYear conventionYear = mConventionYears.get(position);
//        holder.mConventionYearYear.setText(conventionYear.getYearAsString());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
//        String dateString = dateFormat.format(new Date(conventionYear.getStartDate())) + " to " +
//                dateFormat.format(new Date(conventionYear.getEndDate()));
//        holder.mConventionYearDates.setText(dateString);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mActivity instanceof MainActivity)
//                    ((MainActivity) mActivity).onFragmentInteraction("show conventionYear", conventionYear);
//            }
//        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                if (mActivity instanceof MainActivity)
//                    ((MainActivity) mActivity).onFragmentInteraction("edit conventionYear", conventionYear);
//                return true;
//            }
//        });
//    }

    @Override
    protected void populateViewHolder(ViewHolder holder, ConventionYear conventionYear, int position) {
        holder.mConventionYearYear.setText(conventionYear.getYearAsString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
        String dateString = dateFormat.format(new Date(conventionYear.getStartDate())) + " to " +
                dateFormat.format(new Date(conventionYear.getEndDate()));
        holder.mConventionYearDates.setText(dateString);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity instanceof MainActivity) {
//                    ((MainActivity) mActivity).onFragmentInteraction("show conventionYear", conventionYear);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mActivity instanceof MainActivity) {
//                    ((MainActivity) mActivity).onFragmentInteraction("edit conventionYear", conventionYear);
                }
                return true;
            }
        });
    }

//    @Override
//    public int getItemCount() {
//        return mConventionYears.size();
//    }
//
//    public void updateData(List<ConventionYear> conventionYears) {
//        mConventionYears = conventionYears;
//        notifyDataSetChanged();     // TODO: Setup Animations
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.convention_year) TextView mConventionYearYear;
        @BindView(R.id.convention_dates) TextView mConventionYearDates;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
