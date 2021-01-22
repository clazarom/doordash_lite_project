package com.catlaz.doordash_lit_cl;

import android.content.Context;

import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.ui.main.MainFragment;
import com.catlaz.doordash_lit_cl.ui.main.RestaurantsFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test class to verify the main activity screens load properly
 */
public class MainActivityInstrumentedTest {

    private static final int _MAX_REST_NUM = 50;
    private static final int _MAX_WAIT_TIME = 30; //seconds
    private static final String _MAIN_FRAGMENT_TAG = "main_restaurants_fragment";

    Context appContext;
    MainActivity mMainActivity;
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setup(){
        appContext = getInstrumentation().getTargetContext();

        //Launch MainActivity
        mMainActivity = activityTestRule.getActivity();
        mMainActivity.getSupportFragmentManager().beginTransaction();

    }

    @After
    public void clean(){
        activityTestRule.finishActivity();
    }


    /**
     * Test the activity has been launched
     */
    @Test
    public void test1LaunchActivity() {
        assertNotNull(activityTestRule.getActivity());
    }

    /**
     * Test the first fragment loaded in mainActivity is the right one "main_fragment"
     * Perform some basic UI tests
     */
    @Test
    public void test2InitialFragment(){
        //Fragment holder
        onView(withId(R.id.fragemt_placeholder)).check(matches(isDisplayed()));

        //Check the views inside main_fragment
        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()));

        //The first page on view_pager should be "fragment_page_restaurant"
        ViewInteraction viewListInteraction = onView(withId(R.id.list_restaurants));
        viewListInteraction.check(matches(isDisplayed()));
        onView(withId(R.id.refresh_button)).check(matches(isDisplayed()));
    }

    /**
     * Test we can scroll and move back and forth in the ViewPager
     */
    @Test
    public void test3ViewPager(){
        //Check first child of viewpager - RestaurantsFragment
        onView(withId(R.id.fragemt_placeholder)).check(matches(isDisplayed()));

        //Check swiping left --> move to second page --> <TODO>FAILING (needs to fix the scrolling list/viespager)
        //onView(withId(R.id.view_pager)).perform(swipeLeft());
        //Check click on TAB 2
        onView(withText(R.string.tab_text_2)).perform(click());
        onView(withId(R.id.fragment_page_map)).check(matches(isDisplayed()));

        //Check swiping right --> move to first page
        onView(withId(R.id.view_pager)).perform(swipeRight());
        onView(withId(R.id.fragment_page_restaurants)).check(matches(isDisplayed()));
        //Check click on TAB 1
        onView(withText(R.string.tab_text_2)).perform(click());
        onView(withText(R.string.tab_text_1)).perform(click());
        onView(withId(R.id.fragment_page_restaurants)).check(matches(isDisplayed()));
    }

    /**
     * Test the restaurant list is filled when clicking the refresh button
     * Check that items can be clicked too.
     */
    @Test
    public void test4LoadRestaurantList(){
        //Click refresh button: download restaurants
        onView(withId(R.id.refresh_button)).perform(click());

        //WAIT FOR THE LIST TO FILL: max acceptable waiting time
        try {
            Thread.sleep(_MAX_WAIT_TIME*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Check last item of the restaurant list
        onData(anything())
                .inAdapterView(withId(R.id.list_restaurants))
                .atPosition(_MAX_REST_NUM-1)
                .check(matches(isDisplayed()));

        //Test  click on first item
        onData(anything())
                .inAdapterView(withId(R.id.list_restaurants))
                .atPosition(0)
                .check(matches(isDisplayed())).perform(click());
    }

    /**
     * Test the app can load the Restaurant's Details properly
     */
    @Test
    public void test5RestaurantDetailsAndNavigation(){
        //1. Get current fragment
        FragmentManager fm = mMainActivity.getSupportFragmentManager();
        if (fm.getBackStackEntryCount()>0) {
            MainFragment mFragment = (MainFragment) fm.findFragmentByTag(_MAIN_FRAGMENT_TAG);
            RestaurantsFragment restaurantsFragment = (RestaurantsFragment) mFragment.getCurrentPage();

            //2. Click refresh button: download restaurants
            onView(withId(R.id.refresh_button)).perform(click());
            //WAIT FOR THE LIST TO FILL: <TODO> implement idle resources
            boolean listEmpty = true;
            while (listEmpty) {
                try {
                    onData(anything())
                            .inAdapterView(withId(R.id.list_restaurants))
                            .atPosition(_MAX_REST_NUM - 1)
                            .check(matches(isDisplayed()));

                    listEmpty = false;
                }catch (java.lang.RuntimeException e){
                    //items did not load
                }
            }

            //3. Test  click on a random item
            int upperLimit = _MAX_REST_NUM - 1;
            int lowerLimit = 0;
            int randomPosition = (int) (Math.random() * (upperLimit - lowerLimit) - lowerLimit);
            onData(anything())
                    .inAdapterView(withId(R.id.list_restaurants))
                    .atPosition(randomPosition)
                    .check(matches(isDisplayed())).perform(click());

            //4. Check details restaurant is displayed:
            onView(withId(R.id.fragment_rest_detail)).check(matches(isDisplayed()));
            //Also, check that the data display matches the Restaurant
            Restaurant restaurant = restaurantsFragment.getrListAdapter().getRestaurantsList().get(randomPosition);
            onView(withId(R.id.restaurant_name_detail)).check(matches(withText(restaurant.getName())));
            onView(withId(R.id.restaurant_description_detail)).check(matches(withText(restaurant.getDescription())));

            //5.Go back to restaurants list
            Espresso.pressBack();
            onView(withId(R.id.fragment_page_restaurants)).check(matches(isDisplayed()));

        }else
            fail(); // app was not started properly
    }


    @Test
    public void test6LifecycleOwnerActivity(){
        //Right after launch> CREATED
//        System.out.println("lifecycle: "+activityTestRule.getActivity().getLifecycle().getCurrentState() +
//                " VS "+CREATED);
//
//        assert(activityTestRule.getActivity().getLifecycle().equals(Lifecycle.State.CREATED));

        // Change state: STARTED
        // activityTestRule.getActivity().moveToState(STARTED);

//        // Change state: RESUMED
//        activityScenario.moveToState(RESUMED);
//        activityScenario.onActivity(
//                activity -> {
//                    System.out.println("lifecycle: "+activity.getLifecycle().getCurrentState());
//                    assert(activity.getLifecycle().getCurrentState()).equals(RESUMED);
//                });

    }
}
