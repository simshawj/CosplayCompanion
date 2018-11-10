package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.controllers.ShowConventionController;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionDialogFragment;
import com.jamessimshaw.cosplaycompanion.models.Convention;
import com.squareup.picasso.Picasso;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/4/15.
 */
public class ConventionRecViewAdapter extends FirebaseRecyclerAdapter<Convention, ConventionRecViewAdapter.ViewHolder> {
    private Activity mActivity;
    private Router mRouter;
    private @LayoutRes int mModelLayout;

    public ConventionRecViewAdapter(Class<Convention> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, DatabaseReference reference, Activity activity, Router router) {
        super(new FirebaseRecyclerOptions.Builder<Convention>()
                .setQuery(reference, modelClass)
                .build());
        mModelLayout = modelLayout;
        mActivity = activity;
        mRouter = router;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull final Convention convention) {
        viewHolder.conventionNameTextView.setText(convention.getName());
        // TODO: Create a error and placeholder images
        Picasso.get()
                .load(convention.getLogoUriString()).fit().centerInside()
                .into(viewHolder.conventionLogoImageView);
        viewHolder.conventionDescriptionTextView.setText(convention.getDescription());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity instanceof MainActivity) {
                    DatabaseReference reference = getRef(viewHolder.getAdapterPosition());
                    mRouter.pushController(RouterTransaction.with(ShowConventionController.newInstance(reference.toString())).pushChangeHandler(new HorizontalChangeHandler()).popChangeHandler(new HorizontalChangeHandler()));
                }
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (uid.equals(convention.getSubmitted())) {
                    if (mActivity instanceof MainActivity) {
                        DatabaseReference reference = getRef(viewHolder.getAdapterPosition());
                        ModifyConventionDialogFragment modifyConventionDialogFragment = ModifyConventionDialogFragment.newInstance(reference.toString());
                        modifyConventionDialogFragment.show(mActivity.getFragmentManager(), "Modify Convention");
                    }
                } else {
                    Toast.makeText(mActivity, "Only user who created the convention can edit.", Toast.LENGTH_SHORT).show();
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

        @BindView(R.id.convention_name) TextView conventionNameTextView;
        @BindView(R.id.convention_logo) ImageView conventionLogoImageView;
        @BindView(R.id.conDescriptionTextView) TextView conventionDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
