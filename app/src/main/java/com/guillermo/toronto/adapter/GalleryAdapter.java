package com.guillermo.toronto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillermo.toronto.R;
import com.guillermo.toronto.app.App;
import com.guillermo.toronto.imports.domain.Array;
import com.guillermo.toronto.imports.domain.Dict;
import com.guillermo.toronto.imports.domain.PListObject;
import com.guillermo.toronto.view.GalleryViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alvaregd on 24/05/16.
 * Populate the Favourited items Gallery
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    private static final String TAG = AttractionsAdapter.class.getName();

    /** text information for each gallery section */
    private static List<String> information;
    private Context context;
    private int selectedGallery = 0;

    /** the title of the selected item */
    private String selectedPlace;

    public String getSelectedUrl() {
        return selectedUrl;
    }

    private String selectedUrl;

    /** Load information for the gallery based on the selected favourite item*/
    public GalleryAdapter( Context context, int selectedGallery) {
        this.context = context;
        this.selectedGallery = selectedGallery;

        try {

            information = new ArrayList<>(6);
            Array array = (Array) AttractionsAdapter.pList.getRootElement();
            Dict dict = (Dict)array.get(selectedGallery);
            Map<String, PListObject> map = dict.getConfigMap();
            selectedPlace = ((com.guillermo.toronto.imports.domain.String) map.get("title")).getValue();
            selectedUrl = ((com.guillermo.toronto.imports.domain.String) map.get("url")).getValue();

            for(int i = 0; i < 7; ++i) {
                information.add("Fun fact " + String.valueOf(i) + " for " + selectedPlace +": This place is " +
                        "super awesome! Everyone should go right now! When I say now I mean now! If you are driving" +
                        "somewhere turn around! If you are at home chilling, you should chill at this place. " +
                        "If you are working, you should head over there with your co-workers. This Place is Awesome");
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "GalleryAdapter: Unable to fetch pList data", e);
        }
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent,false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        // We don't have any images, for now just set every entry to contain same image, text and title
        holder.imageView.setImageResource(FavouriteListAdapter.item_to_resourceId.get(selectedGallery));
        holder.title.setText(selectedPlace);
        holder.descriptionTextView.setText(information.get(position));
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    /** get App from application's context */
    private App getApplication(){
        return ((App) context.getApplicationContext());
    }

}
