package com.catlaz.doordash_lit_cl;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RestaurantsFragmentInstrumentedTest {

    //Launch MainActivity
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityTestRule  = new  ActivityScenarioRule<>(MainActivity.class);



    @Before
    public void setup(){
//        activityActivityTestRule.getActivity()
//                .getSupportFragmentManager().beginTransaction();

    }


    @Test
    public void testSetup(){
        //Check components are visible
        onView(withId(R.id.refresh_button)).check(matches(isDisplayed()));
        onView(withId(R.id.more_button)).check(matches(isDisplayed()));

    }
}
