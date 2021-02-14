package com.catlaz.doordash_lit_cl;

import android.app.Instrumentation;
import android.content.Context;

import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.ui.main.RestaurantsAdapter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RestaurantListInstrumentedTests {
    Context appContext;
    RestaurantsAdapter restaurantsAdapter;
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        restaurantsAdapter = new RestaurantsAdapter();
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        assertEquals("com.catlaz.doordash_lit_cl.debug", appContext.getPackageName());
    }

    @Test
    public void listview_Adds_Restaurants() {
        //1. Update with MOCK Restaurants list
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "katsu burger", "burgers, korean", "url"));
        restaurantList.add(new Restaurant(2, "howl at the moon", "italian, pasta", "url"));
        restaurantList.add(new Restaurant(3, "pagliaci", "italian, pizza", "url"));
        //Update list
        restaurantsAdapter.updateRestaurantList(restaurantList, null);
        assertEquals(4, 2 + 2);
    }
}