package com.guillermo.toronto;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import com.guillermo.toronto.activity.MainActivity;
import com.guillermo.toronto.imports.PListXMLHandler;
import com.guillermo.toronto.imports.PListXMLParser;
import com.guillermo.toronto.imports.domain.Array;
import com.guillermo.toronto.imports.domain.PList;
import com.robotium.solo.Solo;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by alvaregd on 25/05/16.
 */
public class FavouriteTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public FavouriteTest() {
        super(MainActivity.class);
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

        solo.unlockScreen();

        //check that recycler view is there.
        solo.getView((RecyclerView) solo.getView(com.guillermo.toronto.R.id.display_view));

        //check that recycler view has items
        assertTrue(((RecyclerView)solo.getView(com.guillermo.toronto.R.id.display_view)).getAdapter().getItemCount() > 0);

        //Click on an item to favourite ,
        solo.clickOnView(solo.getView(com.guillermo.toronto.R.id.ll_item));

        Activity myActivity = getActivity();
        // make activity falling into restart phase:
        getInstrumentation().callActivityOnRestart(myActivity);

        //lock the screen
        getInstrumentation().waitForIdleSync();

        //click on an item to unfavourite
        solo.clickOnView(solo.getView(com.guillermo.toronto.R.id.ll_item));

    }

    public void testDataLoad() throws IOException{

        MainActivity activity = getActivity();

        PList pList = null;
        PListXMLParser parser = new PListXMLParser();
        PListXMLHandler handler = new PListXMLHandler();
        parser.setHandler(handler);

        parser.parse(IOUtils.toString(activity.getResources().openRawResource(R.raw.places)));
        pList = ((PListXMLHandler) parser.getHandler()).getPlist();

        Array array = (Array)pList.getRootElement();
        assertTrue(array.size() > 0);
    }
}
