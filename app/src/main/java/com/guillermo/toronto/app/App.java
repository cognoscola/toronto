package com.guillermo.toronto.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.google.gson.Gson;
import com.guillermo.toronto.data.Appdata;

/**
 * Created by alvaregd on 24/05/16.
 * Takes care of storing / saving data internally and also the place
 * where our application data lives while the app is running
 */
public class App extends Application {
    private static final String TAG = App.class.getName();

    public static final String COLLECTION_KEY = "COLKEY";
    public static final String PREF_KEY = "PREFKEY";

    /** convert data to json string and back */
    private static Gson gson;
    /** Our application data, aka our favourite lists*/
    private Appdata data;

    static {
        gson = new Gson();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base); // google gms maps, may require this
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Fetch data from internal storage
      * @param prefs the shared preferences to fetch data from
     * @param gson tool used to convert data from String to Object
     * @return the Appdata
     */
    private Appdata LoadCollection(SharedPreferences prefs, Gson gson) {
        Appdata collection;
        String data = prefs.getString(COLLECTION_KEY, "");
        if (data.isEmpty() | data.equals("")) {
            collection = new Appdata();
        }else {
            collection = gson.fromJson(data, Appdata.class);
            Log.d(TAG, "LoadCollection: Load Successful");
        }
        return collection;
    }

    /**
     * Save the data to internal storage
     * @param prefs  the shared preferences to save data to
     * @param gson tool used to convert data from String to Object
     */
    private void saveCollection(SharedPreferences prefs, Gson gson) {
        if (data != null) {
            String dataString = gson.toJson(data);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(COLLECTION_KEY, dataString);
            edit.apply();
        }
    }

    /**
     * Get application data
     * @return the application data
     */
    public Appdata getData(){

        if (data == null) {
            data = LoadCollection(getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE), gson);
        }
        if (gson == null) {
            gson = new Gson();
        }
        return data;
    }

    /**
     * Command to tell application to save data because it was recently changed
     */
    public void persistData(){
        if (data == null) {
            return;
        }
        if (gson == null) {
            gson = new Gson();
        }
        saveCollection(getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE), gson);
    }
}
