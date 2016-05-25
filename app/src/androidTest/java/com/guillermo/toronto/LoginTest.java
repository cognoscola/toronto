package com.guillermo.toronto;

import android.test.ActivityInstrumentationTestCase2;
import com.guillermo.toronto.activity.LoginActivity;
import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private final static String username = "guillermo@toronto.com";
    private final static String password = "toronto";

    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
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

        solo.unlockScreen();
        //set the text
        solo.getEditText(com.guillermo.toronto.R.id.email).setText(username);
        solo.getEditText(com.guillermo.toronto.R.id.email).setText(password);
        //login,
        solo.clickOnView(solo.getView(com.guillermo.toronto.R.id.email_sign_in_button));

    }
}