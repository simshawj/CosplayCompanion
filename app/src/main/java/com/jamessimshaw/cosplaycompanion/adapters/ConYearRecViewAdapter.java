package com.jamessimshaw.cosplaycompanion.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.jamessimshaw.cosplaycompanion.R;
import com.jamessimshaw.cosplaycompanion.activities.MainActivity;
import com.jamessimshaw.cosplaycompanion.controllers.ShowConventionYearController;
import com.jamessimshaw.cosplaycompanion.fragments.ModifyConventionYearDialogFragment;
import com.jamessimshaw.cosplaycompanion.models.ConventionYear;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 10/11/15.
 */
public class ConYearRecViewAdapter extends FirebaseRecyclerAdapter<ConventionYear, ConYearRecViewAdapter.ViewHolder> {
    private Activity mActivity;
    private Router mRouter;
    private @LayoutRes int mModelLayout;

    public ConYearRecViewAdapter(Class<ConventionYear> modelClass, @LayoutRes int modelLayout, Class<ViewHolder> viewHolderClass, Query keyQuery, DatabaseReference dataRef, Activity activity, Router router) {
        super( new FirebaseRecyclerOptions.Builder<ConventionYear>()
                .setIndexedQuery(keyQuery, dataRef, modelClass)
                .build());
        mModelLayout = modelLayout;
        mActivity = activity;
        mRouter = router;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int i, @NonNull final ConventionYear conventionYear) {
        holder.mConventionYearDisplayName.setText(conventionYear.getDisplayName());
        final SimpleDateFormat dateFormat = new SimpleDateFormat("cccc MMMM dd", Locale.getDefault());
        String dateString = dateFormat.format(new Date(conventionYear.getStartDate())) + " to " +
                dateFormat.format(new Date(conventionYear.getEndDate()));
        holder.mConventionYearDates.setText(dateString);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity instanceof MainActivity) {
                    mRouter.pushController(RouterTransaction.with(ShowConventionYearController.newInstance(getRef(holder.getAdapterPosition()).toString())).pushChangeHandler(new HorizontalChangeHandler()).popChangeHandler(new HorizontalChangeHandler()));
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (uid.equals(conventionYear.getSubmitted())) {
                    if (mActivity instanceof MainActivity) {
                        ModifyConventionYearDialogFragment modifyConventionYearDialogFragment = ModifyConventionYearDialogFragment.newInstance(getRef(holder.getAdapterPosition()).toString(), true);
                        modifyConventionYearDialogFragment.show(mActivity.getFragmentManager(), "Modify Convention Year");
                    }
                } else {
                    Toast.makeText(mActivity, "Only user who created the event can edit.", Toast.LENGTH_SHORT).show();
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

        @BindView(R.id.convention_year) TextView mConventionYearDisplayName;
        @BindView(R.id.convention_dates) TextView mConventionYearDates;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
