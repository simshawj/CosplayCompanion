package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Router;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyPhotoshootDialogFragment;
import com.jamessimshaw.cosplaycompanion.models.Photoshoot;

import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/16/15.
 */
public class PhotoshootRecViewAdapter extends FirebaseRecyclerAdapter<Photoshoot, PhotoshootRecViewAdapter.ViewHolder> {
    private Activity mActivity;
    private Router mRouter;
    private @LayoutRes int mModelLayout;

    public PhotoshootRecViewAdapter(Class<Photoshoot> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, Query keyQuery, DatabaseReference dataRef, Activity activity, Router router) {
        super(new FirebaseRecyclerOptions.Builder<Photoshoot>()
                .setIndexedQuery(keyQuery, dataRef, modelClass)
                .build());
        mModelLayout = modelLayout;
        mActivity = activity;
        mRouter = router;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final Photoshoot photoshoot) {
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
                        ModifyPhotoshootDialogFragment modifyPhotoshootDialogFragment = ModifyPhotoshootDialogFragment.newInstance(getRef(viewHolder.getAdapterPosition()).toString(), true);
                        modifyPhotoshootDialogFragment.show(mActivity.getFragmentManager(), "Modify Photoshoot");
                    }
                } else {
                    Toast.makeText(mActivity, "Only user who created the photoshoot can edit.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mActivity).inflate(mModelLayout, parent, false));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.seriesTextView) TextView mPhotoshootSeries;
        @BindView(R.id.dateTextView) TextView mPhotoshootDate;
        @BindView(R.id.locationTextView) TextView mPhotoshootLocation;
        @BindView(R.id.descriptionTextView) TextView mPhotoshootDescription;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
