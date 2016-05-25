package com.guillermo.toronto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillermo.toronto.R;
import com.guillermo.toronto.activity.GalleryListener;
import com.guillermo.toronto.app.App;
import com.guillermo.toronto.view.FavouriteViewHolder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by alvaregd on 24/05/16.
 * Populates the Favourite list (The list found in the actionbar of the FavouritesActivity)
 */
public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    private static final String TAG = FavouriteListAdapter.class.getName();

    /** map the item position to the item in our PLIST */
    public static HashMap<Integer, Integer> position_to_item;
    /** map the pLIST item to the correct Image Resource */
    public static HashMap<Integer, Integer> item_to_resourceId;

    /** which item is corrently selected**/
    private int selectedItem = 0;

    private Context context;

    /** notify parent activity we have switched to a different gallery */
    private GalleryListener galleryListener;

    /** prepare to fetch image resources */
    public FavouriteListAdapter( Context context) {
        this.context = context;
        this.galleryListener = ((GalleryListener) context);

        //map PLIST item to correct image resource */
        position_to_item = new HashMap<>(10);
        item_to_resourceId = new HashMap<>(10);
        item_to_resourceId.put(0, R.drawable.aquarium);
        item_to_resourceId.put(1, R.drawable.cntower);
        item_to_resourceId.put(2, R.drawable.torontozoo);
        item_to_resourceId.put(3, R.drawable.rom);
        item_to_resourceId.put(4, R.drawable.artgalleryontario);
        item_to_resourceId.put(5, R.drawable.yorkdalemall);
        item_to_resourceId.put(6, R.drawable.torontoeatoncentre);
        item_to_resourceId.put(7, R.drawable.torontocityhall);
        item_to_resourceId.put(8, R.drawable.halloffame);
        item_to_resourceId.put(9, R.drawable.canadacenter);

        App app = getApplication();
        HashSet<Integer> favourites = app.getData().getFavourited();

        Iterator iter = favourites.iterator();
        int count = 0;
        while (iter.hasNext()) {
            int favourite = (Integer) iter.next();
            if (count == 0) {
                galleryListener.onItemSelected(favourite);
            }
            Log.d(TAG, "FavouriteListAdapter: Favourite:" + favourite);
            position_to_item.put(count, favourite);
            count++;
        }
        selectedItem = 0;
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item, parent,false);
        return new FavouriteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final FavouriteViewHolder holder, final int position) {

        /** Corrently populate the list with the User's Favourites and set each item
         * clickable
         */
        if (item_to_resourceId != null && position_to_item != null){

            /** if we somehow selected an item with incorrect mapping, do not change th view **/
            final Integer pos2Item = position_to_item.get(position);
            if(pos2Item == null) return;

            final Integer resId = item_to_resourceId.get(pos2Item);
            if(resId == null) return;

            holder.imageView.setImageResource(resId);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem = position;
                    notifyDataSetChanged();
                    //notify parent of changed selection
                    galleryListener.onItemSelected(pos2Item);
                }
            });

            /** change the background of selected / unselected items **/
            if (position == selectedItem) {
                holder.rootView.setBackgroundColor(Color.parseColor("#FF999999"));
            }else{
                holder.rootView.setBackgroundResource(R.drawable.grey_background);
            }
        }
    }

    @Override
    public int getItemCount() {
        return position_to_item.size();
    }

    /** get App from application's context */
    private App getApplication(){
        return ((App) context.getApplicationContext());
    }

}