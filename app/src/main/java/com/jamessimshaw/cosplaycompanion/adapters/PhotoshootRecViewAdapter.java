package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Router;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyPhotoshootDialogFragment;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/16/15.
 */
public class PhotoshootRecViewAdapter extends FirebaseIndexRecyclerAdapter<Photoshoot, PhotoshootRecViewAdapter.ViewHolder> {
    private Activity mActivity;
    private Router mRouter;

    public PhotoshootRecViewAdapter(Class<Photoshoot> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, Query keyQuery, DatabaseReference dataRef, Activity activity, Router router) {
        super(modelClass, modelLayout, viewHolderClass, keyQuery, dataRef);
        mActivity = activity;
        mRouter = router;
    }

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, final Photoshoot photoshoot, final int position) {
        viewHolder.mPhotoshootSeries.setText(photoshoot.getSeries());
        viewHolder.mPhotoshootDescription.setText(photoshoot.getDescription());
        viewHolder.mPhotoshootLocation.setText(photoshoot.getLocation());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE MMMM dd yyyy @ hh:mm aa",
                Locale.getDefault());
        viewHolder.mPhotoshootDate.setText(dateFormat.format(photoshoot.getStart()));
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (uid.equals(photoshoot.getSubmitted())) {
                    if (mActivity instanceof MainActivity) {
                        ModifyPhotoshootDialogFragment modifyPhotoshootDialogFragment = ModifyPhotoshootDialogFragment.newInstance(getRef(position).toString(), true);
                        modifyPhotoshootDialogFragment.show(mActivity.getFragmentManager(), "Modify Photoshoot");
                    }
                } else {
                    Toast.makeText(mActivity, "Only user who created the photoshoot can edit.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.seriesTextView) TextView mPhotoshootSeries;
        @BindView(R.id.dateTextView) TextView mPhotoshootDate;
        @BindView(R.id.locationTextView) TextView mPhotoshootLocation;
        @BindView(R.id.descriptionTextView) TextView mPhotoshootDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
