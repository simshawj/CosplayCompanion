package com.jamessimshaw.cosplaycompanion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamessimshaw.cosplaycompanion.R;

/**
 * Created by james on 9/30/15.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder> {
    private static final int HEADER = 0;
    private static final int ROW = 1;

    private String mItems[];
    private int mIcons[];

    public NavAdapter(String items[], int icons[]) {
        mIcons = icons;
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch(viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_drawer_header, parent, false);
                break;
            case ROW:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_drawer_item_row, parent, false);
                break;
            default: return null;
        }
        ViewHolder viewHolder = new ViewHolder(view, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.mId == ROW) {
            holder.mItemTextView.setText(mItems[position - 1]);
            holder.mIconImageView.setImageResource(mIcons[position - 1]);
        } else {

        }

    }

    @Override
    public int getItemCount() {
        return mItems.length + 1;  // Number of items plus 1 for the header
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER;
        else
            return ROW;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private int mId;
        private TextView mItemTextView;
        private ImageView mIconImageView;


        public ViewHolder(View itemView, int itemType) {
            super(itemView);

            mId = itemType;
            switch (itemType) {
                case HEADER:break;
                case ROW:
                    mItemTextView = (TextView) itemView.findViewById(R.id.navDrawerRowText);
                    mIconImageView = (ImageView) itemView.findViewById(R.id.navDrawerRowIcon);
                    break;
                default: break;
            }
        }
    }
}
