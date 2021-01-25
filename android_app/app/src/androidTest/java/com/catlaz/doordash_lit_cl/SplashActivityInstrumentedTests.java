package com.catlaz.doordash_lit_cl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

public class SplashActivityInstrumentedTests {
    SplashActivity mSplashActivity;
    @Rule
    public final ActivityTestRule<SplashActivity> activityTestRule = new ActivityTestRule<>(SplashActivity.class);


    @Before
    public void setup(){
        //Get context
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //Launch SplashActivity
        mSplashActivity = activityTestRule.getActivity();
        mSplashActivity.getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testInternet(){
        mSplashActivity.networkStateManager.isNetworkAvailable();
    }

    @Test
    public void testNotification(){
        mSplashActivity.showNotification("TestNotification");
    }
}
