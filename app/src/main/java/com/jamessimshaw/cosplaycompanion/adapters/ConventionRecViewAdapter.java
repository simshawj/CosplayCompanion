package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Convention convention = mConventions.get(position);
        holder.mConventionNameTextView.setText(convention.getName());
        Picasso.with(mActivity)
                .load(convention.getLogoUriString()).fit().centerInside()
                .into(holder.mConventionLogoImageView);
        holder.mConventionDescriptionTextView.setText(mConventions.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity instanceof MainActivity)
                    ((MainActivity) mActivity).onFragmentInteraction("show convention", convention);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mActivity instanceof  MainActivity)
                    ((MainActivity) mActivity).onFragmentInteraction("edit convention", convention);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mConventions.size();
    }

    public void addNewConventions(List<Convention> conventionList) {
        mConventions.addAll(conventionList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.convention_name) TextView mConventionNameTextView;
        @BindView(R.id.convention_logo) ImageView mConventionLogoImageView;
        @BindView(R.id.conDescriptionTextView) TextView mConventionDescriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
