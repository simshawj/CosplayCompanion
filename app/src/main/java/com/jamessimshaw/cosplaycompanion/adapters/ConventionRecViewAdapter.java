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

import java.util.ArrayList;

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
        ViewHolder holder = new ViewHolder(view, mActivity);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mConventionNameTextView.setText(mConventions.get(position).getName());
        holder.mConventionLogoImageView.setImageBitmap(mConventions.get(position).getLogo());
        holder.mConventionDescriptionTextView.setText(mConventions.get(position).getDescription());
        holder.mConvention = mConventions.get(position);
    }

    @Override
    public int getItemCount() {
        return mConventions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mConventionNameTextView;
        private ImageView mConventionLogoImageView;
        private TextView mConventionDescriptionTextView;
        private Activity mActivity;
        private Convention mConvention;

        public ViewHolder(View itemView, Activity activity) {
            super(itemView);

            mActivity = activity;
            mConventionLogoImageView = (ImageView) itemView.findViewById(R.id.convention_logo);
            mConventionNameTextView = (TextView) itemView.findViewById(R.id.convention_name);
            mConventionDescriptionTextView = (TextView) itemView.findViewById(R.id.conDescriptionEditText);

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (mActivity instanceof MainActivity)
                ((MainActivity)mActivity).switchToConventionFragment(mConvention);
        }
    }
}
