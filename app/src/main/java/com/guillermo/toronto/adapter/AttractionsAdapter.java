package com.guillermo.toronto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.guillermo.toronto.R;
import com.guillermo.toronto.activity.FavouriteListener;
import com.guillermo.toronto.app.App;
import com.guillermo.toronto.imports.PListXMLHandler;
import com.guillermo.toronto.imports.PListXMLParser;
import com.guillermo.toronto.imports.domain.Array;
import com.guillermo.toronto.imports.domain.Dict;
import com.guillermo.toronto.imports.domain.PList;
import com.guillermo.toronto.imports.domain.PListObject;
import com.guillermo.toronto.view.AttractionsViewHolder;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alvaregd on 24/05/16.
 * Populates the Recycler view found on the MainActivity page (list of attractions)
 */
public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsViewHolder> {
    private static final String TAG = AttractionsAdapter.class.getName();

    /** the titles for each place **/
    private List<String> labels;
    /** images for each entry */
    private static List<Integer> resourcesIds;
    /** our Plist Data */
    public static PList pList;

    private Context context;

    /** listen to when the user has clicked an item */
    private FavouriteListener listener;

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    private boolean isGrid = false;

    /** prepare to fetch image resources */
    static{
        resourcesIds = new ArrayList<>();
        resourcesIds.add(R.drawable.aquarium);
        resourcesIds.add(R.drawable.cntower);
        resourcesIds.add(R.drawable.torontozoo);
        resourcesIds.add(R.drawable.rom);
        resourcesIds.add(R.drawable.artgalleryontario);
        resourcesIds.add(R.drawable.yorkdalemall);
        resourcesIds.add(R.drawable.torontoeatoncentre);
        resourcesIds.add(R.drawable.torontocityhall);
        resourcesIds.add(R.drawable.halloffame);
        resourcesIds.add(R.drawable.canadacenter);
    }

    /** Fetch data from the Plist and populate the views accordingly */
    public AttractionsAdapter(int count, Context context) {
        this.context = context;
        this.listener = (FavouriteListener) context;
        //Initialize labels to default value
        labels = new ArrayList<>(count);
        for(int i = 0 ; i <count; ++i) {
            labels.add(String.valueOf(i));
        }

        /* Fetch data from the XML pList. Because the data to retrieve is small it can be
         * done synchronously, but if size is large move to Async task   */
        pList = null;
        PListXMLParser parser = new PListXMLParser();
        PListXMLHandler handler = new PListXMLHandler();
        parser.setHandler(handler);
        try {
            parser.parse(IOUtils.toString(context.getResources().openRawResource(R.raw.places)));
            pList = ((PListXMLHandler) parser.getHandler()).getPlist();

        } catch (IOException e) {
            Log.d(TAG, "AttractionsAdapter: Something went wrong when attempting to get PLIST data",e);
        }
    }

    @Override
    public AttractionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new AttractionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AttractionsViewHolder holder, final int position) {

        final String label = labels.get(position);

        /** get the correct title value from the pList data and set it visible */
        if (pList != null) {
            Array array = (Array)pList.getRootElement();
            Dict dict = (Dict)array.get(position);
            Map<String, PListObject> map = dict.getConfigMap();
            holder.textView.setText(((com.guillermo.toronto.imports.domain.String)map.get("title")).getValue());
        }

        holder.imageView.setImageResource(resourcesIds.get(position));
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Check if we aren't already favourited, If yes, remove the favourited item,
                 * otherwise add the favourite. In Any case, always persist the change in internal
                 * storage
                 */
                if (isFavourited(position)) {
                    getApplication().getData().getFavourited().remove(position);
                    setFavouriteOff(holder);
                }else{
                    listener.OnFavourited(holder.textView.getText().toString());
                    getApplication().getData().getFavourited().add(position);
                    setFavouriteOn(holder);
                }
                getApplication().persistData();
                Toast.makeText(
                        holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
            }
        });

        /**Check if this item is in our favourited list, and if so change the favourite ICON */
        if (isGrid) {

            //but first check if the favourite icon should even be visible
            holder.favouriteImageView.setVisibility(View.GONE);
            holder.textView.setTextSize(14);
        }else{
            holder.favouriteImageView.setVisibility(View.VISIBLE);
            holder.textView.setTextSize(20);
            if (isFavourited(position)) {
                setFavouriteOn(holder);
            }else{
                setFavouriteOff(holder);
            }
        }
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

    /** get App from application's context */
    private App getApplication(){
        return ((App) context.getApplicationContext());
    }

    /**
     * Check that the current item is already favourited by the user
     * @param i the incoming index number
     * @return wether this item is in our favourite list or not
     */
    private boolean isFavourited(int i){
        return getApplication().getData().getFavourited().contains(i);
    }

    /** Change view icon to show NOT favourited */
    private void setFavouriteOff(AttractionsViewHolder holder){
        holder.favouriteImageView.setImageResource(R.drawable.favourite_off);
    }

    /** change view icon to show Favourited */
    private void setFavouriteOn(AttractionsViewHolder holder){
        holder.favouriteImageView.setImageResource(R.drawable.favourite_on);
    }

}
