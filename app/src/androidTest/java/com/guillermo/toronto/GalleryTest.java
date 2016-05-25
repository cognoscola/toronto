package com.guillermo.toronto;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import com.guillermo.toronto.activity.FavouriteActivity;
import com.robotium.solo.Solo;

/**
 * Created by alvaregd on 25/05/16.
 */
public class GalleryTest extends ActivityInstrumentationTestCase2<FavouriteActivity> {

    private Solo solo;

    public GalleryTest() {
        super(FavouriteActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testLogin() throws Exception {

        getInstrumentation().waitForIdleSync();

        //check that gallery is thereis there.
        solo.getView((RecyclerView) solo.getView(R.id.rv_galleryScroll));

        //favourite list is there
        solo.getView((RecyclerView) solo.getView(R.id.rv_favouriteList));

        //check that gallery view has items
        assertTrue(((RecyclerView)solo.getView(com.guillermo.toronto.R.id.rv_galleryScroll)).getAdapter().getItemCount() > 0);
        //check that favourite list has items
        assertTrue(((RecyclerView)solo.getView(com.guillermo.toronto.R.id.rv_favouriteList)).getAdapter().getItemCount() > 0);

    }
}
