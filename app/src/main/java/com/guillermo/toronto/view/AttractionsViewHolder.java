package com.guillermo.toronto.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guillermo.toronto.R;

/**
 * Created by alvaregd on 24/05/16
 * Manage the views for each item in the Attractions ViewHolder
 */
public class AttractionsViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public ImageView imageView;
    public ImageView favouriteImageView;
    public View rootView;

    public AttractionsViewHolder(View itemView) {
        super(itemView);
        this.rootView =  itemView.findViewById(R.id.ll_item);
        this.textView = (TextView) itemView.findViewById(R.id.tv_item_title);
        this.imageView = (ImageView) itemView.findViewById(R.id.iv_item_image);
        this.favouriteImageView = (ImageView) itemView.findViewById(R.id.iv_item_favourite);
    }
}
