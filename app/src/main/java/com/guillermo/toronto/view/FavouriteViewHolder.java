package com.guillermo.toronto.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guillermo.toronto.R;

/**
 * Created by alvaregd on 24/05/16.
 * Manage the views for each item in the Favourites List
 */
public class FavouriteViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public View rootView;

    public FavouriteViewHolder(View itemView) {
        super(itemView);
        this.rootView = itemView.findViewById(R.id.root_view);
        this.imageView = (ImageView) itemView.findViewById(R.id.iv_item_gallery_image);
    }
}
