package com.guillermo.toronto.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guillermo.toronto.R;
import com.guillermo.toronto.adapter.FavouriteListAdapter;
import com.guillermo.toronto.adapter.GalleryAdapter;

/**
 * Shows all of the User's Favourited Attractions
 *  */
public class FavouriteActivity extends AppCompatActivity implements GalleryListener {
    private static final String TAG = FavouriteActivity.class.getName();

    /** shows the selected item gallery **/
    private RecyclerView gallery;
    /** layout configurator for the gallery view*/
    private GridLayoutManager layoutManager;
    /** populates the gallery with pictures and information */
    private GalleryAdapter galleryAdapter;

    /** configures layout for favourite list **/
    private GridLayoutManager favouriteLayoutManager;
    /** shows the list of favourited items **/
    private RecyclerView favouriteList;
    /** populates favourites list */
    private FavouriteListAdapter favouriteListAdapter;

    /** contains the favourite list */
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        //Send the user to a browser with the link from the selected favourite item
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(galleryAdapter.getSelectedUrl()));
                    startActivity(i);
                }
            });
        }

        /* Configure our recycler views and populate them where possible
         */
        favouriteList =  (RecyclerView) findViewById(R.id.rv_favouriteList);
        gallery = (RecyclerView) findViewById(R.id.rv_galleryScroll);

        favouriteList.setNestedScrollingEnabled(false);
        favouriteList.setHasFixedSize(false);
        gallery.setNestedScrollingEnabled(false);
        gallery.setHasFixedSize(false);

        favouriteLayoutManager = new GridLayoutManager(this, 1);
        favouriteLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        favouriteList.setLayoutManager(favouriteLayoutManager);
        favouriteListAdapter = new FavouriteListAdapter(this);
        favouriteList.setAdapter(favouriteListAdapter);

        layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gallery.setLayoutManager(layoutManager);


        //Remove the LINK button of we have no favourites
        if (galleryAdapter == null) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }
        actionBar.setTitle("My Favourites");

    }

    /** Get notified when the user has picked a different favourite item and
     * populate the Gallery view accordingly
     * @param itemId the selected Item ID
     */
    @Override
    public void onItemSelected(int itemId) {
        galleryAdapter = new GalleryAdapter(this,itemId);
        gallery.setAdapter(galleryAdapter);
    }
}
