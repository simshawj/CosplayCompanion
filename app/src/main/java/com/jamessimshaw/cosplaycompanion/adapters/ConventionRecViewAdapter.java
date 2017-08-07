package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
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

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_convention,
//                parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, final Convention convention, int position) {
        viewHolder.conventionNameTextView.setText(convention.getName());
        Picasso.with(mActivity)
                .load(convention.getLogoUriString()).fit().centerInside()
                .into(viewHolder.conventionLogoImageView);
        viewHolder.conventionDescriptionTextView.setText(convention.getDescription());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity instanceof MainActivity)
                    ((MainActivity) mActivity).onFragmentInteraction("show convention", convention);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mActivity instanceof  MainActivity)
                    ((MainActivity) mActivity).onFragmentInteraction("edit convention", convention);
                return true;
            }
        });
    }

//    @Override
//    public int getItemCount() {
//        return mConventions.size();
//    }
//
//    public void addNewConventions(List<Convention> conventionList) {
//        mConventions.addAll(conventionList);
//        notifyDataSetChanged();
//    }

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
