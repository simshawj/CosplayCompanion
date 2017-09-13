package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/4/15.
 */
public class ConventionRecViewAdapter extends FirebaseRecyclerAdapter<Convention, ConventionRecViewAdapter.ViewHolder> {
    private Activity mActivity;

    public ConventionRecViewAdapter(Class<Convention> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, DatabaseReference reference, Activity activity) {
        super(modelClass, modelLayout, viewHolderClass, reference);
        mActivity = activity;
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, final Convention convention, final int position) {
        viewHolder.conventionNameTextView.setText(convention.getName());
        // TODO: Create a error and placeholder images
        Picasso.with(mActivity)
                .load(convention.getLogoUriString()).fit().centerInside()
                .into(viewHolder.conventionLogoImageView);
        viewHolder.conventionDescriptionTextView.setText(convention.getDescription());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity instanceof MainActivity) {
                    DatabaseReference reference = getRef(position);
                    ((MainActivity) mActivity).onFragmentInteraction("show convention", reference.toString());
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mActivity instanceof  MainActivity) {
                    DatabaseReference reference = getRef(position);
                    ((MainActivity) mActivity).onFragmentInteraction("edit convention", reference.toString());
                }
                return true;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.convention_name) TextView conventionNameTextView;
        @BindView(R.id.convention_logo) ImageView conventionLogoImageView;
        @BindView(R.id.conDescriptionTextView) TextView conventionDescriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
