package com.guillermo.toronto.data;

import java.util.HashSet;

/**
 * Created by alvaregd on 24/05/16.
 * Applicaiton Data, our favourite list and wether the user is signed in
 */
public class Appdata {

    /** list of favourited places */
    private HashSet<Integer> favourited;
    /** is the user signed in? */
    private boolean isSignedIn;

    /** GETTERS/SETTERS*/
    public HashSet<Integer> getFavourited() {
        return favourited;
    }

    public void setFavourited(HashSet<Integer> favourited) {
        this.favourited = favourited;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public Appdata() {
        super();
        isSignedIn = false;
        favourited = new HashSet<>(10); //only ten items available in the plist
    }
}
