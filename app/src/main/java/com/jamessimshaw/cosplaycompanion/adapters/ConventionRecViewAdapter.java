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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by james on 10/4/15.
 */
public class ConventionRecViewAdapter extends RecyclerView.Adapter<ConventionRecViewAdapter.ViewHolder> {
    private ArrayList<Convention> mConventions;
    private Activity mActivity;

    public ConventionRecViewAdapter(ArrayList<Convention> conventions, Activity activity) {
        mActivity = activity;
        mConventions = conventions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_convention,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mConventionNameTextView.setText(mConventions.get(position).getName());
        Picasso.with(mActivity)
                .load(mConventions.get(position).getLogoUri()).fit().centerInside()
                .into(holder.mConventionLogoImageView);
        holder.mConventionDescriptionTextView.setText(mConventions.get(position).getDescription());
        holder.mConventionYearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity instanceof MainActivity)
                    ((MainActivity) mActivity).onFragmentInteraction("show convention", mConventions.get(
                            position));
            }
        });
        holder.mConventionEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity instanceof  MainActivity)
                    ((MainActivity) mActivity).onFragmentInteraction("edit convention", mConventions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mConventions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.convention_name) TextView mConventionNameTextView;
        @Bind(R.id.convention_logo) ImageView mConventionLogoImageView;
        @Bind(R.id.conDescriptionTextView) TextView mConventionDescriptionTextView;
        @Bind(R.id.conventionEdit) TextView mConventionEditTextView;
        @Bind(R.id.conventionYearsLink) TextView mConventionYearTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
