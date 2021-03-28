package com.catlaz.doordash_lit_cl;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.catlaz.doordash_lit_cl.Constant._REQ_NUM;
import static org.junit.Assert.assertNotNull;

/**
 * Test class to verify the main activity screens load properly
 */
public class MainActivityInstrumentedTest {

    //Launch the main
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityTestRule  = new  ActivityScenarioRule<>(MainActivity.class);
    ActivityScenario<MainActivity> mainActivityScenario;


    @Before
    public void setup(){
        //Launch MainActivity
        mainActivityScenario = mainActivityTestRule.getScenario();
    }



    /**
     * Test the activity has been launched
     */
    @Test
    public void test1LaunchActivity() {
        assertNotNull(mainActivityScenario);
    }

    /**
     * Test the first fragment loaded in mainActivity is the right one "main_fragment"
     * Perform some basic UI tests
     */
    @Test
    public void test2InitialFragment(){
        //Fragment holder
        onView(withId(R.id.fragment_placeholder)).check(matches(isDisplayed()));

        //Check the views inside main_fragment
        onView(withId(R.id.logo_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()));

        //The first page on view_pager should be "fragment_page_restaurant"
        try {
            //See if loading gif is visible
            ViewInteraction viewListInteraction = onView(withId(R.id.loading_image_layout));
            viewListInteraction.check(matches(isDisplayed()));
        }catch (AssertionFailedError afe){
            //See if list of restaurants is visible
            ViewInteraction viewListInteraction = onView(withId(R.id.list_restaurants));
            viewListInteraction.check(matches(isDisplayed()));
        }
        onView(withId(R.id.refresh_button)).check(matches(isDisplayed()));
    }

    /**
     * Test we can scroll and move back and forth in the ViewPager
     */
    @Test
    public void test3ViewPager(){
        //Check first child of viewpager - RestaurantsFragment
        onView(withId(R.id.fragment_placeholder)).check(matches(isDisplayed()));

        //Check swiping left --> move to second page --> <TODO>FAILING (needs to fix the scrolling list/view_pager)
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
    public void test4RefreshRestaurantList(){
        //Click refresh button: download restaurants
        onView(withId(R.id.refresh_button)).perform(click());


        boolean loading = true;
        while (loading){
            try {
                //See if restaurants list is visible
                ViewInteraction viewListInteraction = onView(withId(R.id.list_restaurants));
                viewListInteraction.check(matches(isDisplayed()));
                //Not loading anymore
                loading = false;
            }catch (AssertionFailedError afe){
                //DO NOTHING: restaurants list loading
            }
        }

        //Check restaurant list is displayed, and scroll to last item
        onView(ViewMatchers.withId(R.id.list_restaurants))
                .perform(RecyclerViewActions.actionOnItemAtPosition(_REQ_NUM-1, scrollTo()));


        //Test  click on first item
        onView(ViewMatchers.withId(R.id.list_restaurants))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    /**
     * Test the app can load the Restaurant's Details properly
     */
    @Test
    public void test5RestaurantNavigation(){
        //1. Wait for restaurants to load
        boolean loading = true;
        while (loading){
            try {
                //See if restaurants list is visible
                ViewInteraction viewListInteraction = onView(withId(R.id.list_restaurants));
                viewListInteraction.check(matches(isDisplayed()));
                //Not loading anymore
                loading = false;
            }catch (AssertionFailedError afe){
                //DO NOTHING: restaurants list loading
            }
        }

        //2. Test  click on a random item
        int upperLimit = _REQ_NUM - 1;
        int lowerLimit = 0;
        int randomPosition = (int) (Math.random() * (upperLimit - lowerLimit) - lowerLimit);
        onView(ViewMatchers.withId(R.id.list_restaurants))
                .perform(RecyclerViewActions.actionOnItemAtPosition(randomPosition, click()));

        //3. Check details restaurant is displayed:
        onView(withId(R.id.fragment_rest_detail)).check(matches(isDisplayed()));

        //4.Go back to restaurants list
        Espresso.pressBack();
        onView(withId(R.id.fragment_page_restaurants)).check(matches(isDisplayed()));

    }

}
