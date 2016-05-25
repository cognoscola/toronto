package com.guillermo.toronto.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.guillermo.toronto.R;
import com.guillermo.toronto.adapter.AttractionsAdapter;

/** Shows Toronto's Main Attractions */
public class MainActivity extends AppCompatActivity implements FavouriteListener {

    /**make sure we are loggged in */
    private FirebaseAuth mAUth;
    /** our display view */
    private RecyclerView displayView;
    /** how our list is displayed */
    private GridLayoutManager layoutManager;
    /** populates the display view */
    private AttractionsAdapter attractionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** change colour to match toronto flag's colour */
        AppBarLayout layout = (AppBarLayout) findViewById(R.id.app_bar);
        if (layout != null) {
            layout.setBackgroundColor(Color.parseColor("#FF274490"));
        }

        /** take user to the MapsActivity */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        /** take user to the Favourites activity */
        FloatingActionButton fabo = (FloatingActionButton) findViewById(R.id.fabourites);
        if (fabo != null) {
            fabo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                    startActivity(intent);
                }
            });
        }

        /** Populate the attraction's list */
        displayView = (RecyclerView) findViewById(R.id.display_view);
        displayView.setNestedScrollingEnabled(false);
        displayView.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(this, 1);
        displayView.setLayoutManager(layoutManager);
        attractionsAdapter = new AttractionsAdapter(10,this);
        displayView.setAdapter(attractionsAdapter);
        mAUth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**Options Menu logic */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_signout:
                /**sign the user out */
                    if (mAUth != null) {
                        mAUth.signOut();
                    }
                return true;
            case R.id.action_grid:
                /** Change the grid appearance to be grid-like */
                if (layoutManager != null) {
                    layoutManager.setSpanCount(2);
                    attractionsAdapter.setGrid(true);
                    attractionsAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.action_list:
                /**change the grid appearance to be a list view */
                if (layoutManager != null) {
                    layoutManager.setSpanCount(1);
                    attractionsAdapter.setGrid(false);
                    attractionsAdapter.notifyDataSetChanged();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnFavourited(String name) {
        Snackbar.make(displayView, "Added " + name + " to Favourites!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
