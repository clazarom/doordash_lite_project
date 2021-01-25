package com.catlaz.doordash_lit_cl;

import android.content.Context;

import com.catlaz.doordash_lit_cl.data.Restaurant;
import com.catlaz.doordash_lit_cl.data.RestaurantDetail;
import com.catlaz.doordash_lit_cl.remote.RestClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.catlaz.doordash_lit_cl.SplashActivity._INIT_REQ_NUM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * local unit tests for the app functionality.
 * - Check  functionality of ViewPager
 * - Check functionality of Restaurants ListVies
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RestClientTest {
    RestClient restClient;
    Context context;


    @Before
    public void setup(){
        context = Mockito.mock(Context.class);
        restClient = new RestClient(context);
    }

    @After
    public void clean(){
        restClient.destroyDisposables();
    }

    @Test
    public void requestDoorDashRestaurants(){
        //No offset request
        List<Restaurant> rList = restClient.testRestaurantsListByDoorDashHQ(0, _INIT_REQ_NUM);
        assertEquals(_INIT_REQ_NUM, rList.size());

        //With offset request
        int offset = 3;
        List<Restaurant> rListOffset = restClient.testRestaurantsListByDoorDashHQ(offset, _INIT_REQ_NUM);
        assertEquals(_INIT_REQ_NUM, rListOffset.size());
        assertEquals(rList.get(offset+1).getId(), rListOffset.get(1).getId());
        assertNotEquals(rList.get(0).getId(), rListOffset.get(0).getId());

    }

    @Test
    public void requestRestaurantDetail(){
        RestaurantDetail rDetail = restClient.getRestaurantDetailSync(30);
        assertNotNull(rDetail);
    }


}