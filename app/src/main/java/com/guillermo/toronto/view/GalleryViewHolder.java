package com.guillermo.toronto.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guillermo.toronto.R;

/**
 * Created by alvaregd on 24/05/16.
 * Manage the views for each item in the Gallery view 
 */
public class GalleryViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView descriptionTextView;
    public TextView title;

    public GalleryViewHolder(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.iv_item_gallery_image);
        this.descriptionTextView = (TextView) itemView.findViewById(R.id.tv_item_gallery_description);
        this.title = (TextView) itemView.findViewById(R.id.tv_gallery_title);
    }
}
