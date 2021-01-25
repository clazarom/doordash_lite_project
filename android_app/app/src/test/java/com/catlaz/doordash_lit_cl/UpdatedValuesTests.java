package com.catlaz.doordash_lit_cl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.catlaz.doordash_lit_cl.data.RestaurantDetail;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static com.catlaz.doordash_lit_cl.MockData._REST_NUM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class UpdatedValuesTests {

    private static final Bitmap testBitmapLogo = mock(Bitmap.class);

    /**
     * Method to quickly paint a simple bitmap
     * Image: "Restaurant Logo"
     * @return logo bitmap
     */
    private static void drawBitmapLogo(){
        //Create bitmap logo
        int w = 300, h = 300; // size
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // Each pixel is stored on 4 bytes.
        Bitmap.createBitmap(w, h, conf);
        //Draw the Bitmap
        Canvas canvas = new Canvas(testBitmapLogo);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(2);
        canvas.drawText("RestaurantLogo", (float)w/2,(float)h/2, paint);
    }
    @Before
    public void setup(){
        //Initialize mock setup
        MockitoAnnotations.initMocks(this);
        //Initialize data
        //logoBitmap = BitmapFactory.decodeFile(getClassLoader().getResource("restaurant_logo.png").getPath());
        drawBitmapLogo();
        MockData.initialize(testBitmapLogo);
    }

    @Test
    public void updateRestaurantListTest(){
        //1. Add new list and check it's accessible
        UpdatedValues.Instance().updateRestaurants(MockData.getRestaurantList(), MockData.getRestaurantLogoMap());
        //Check size of the LIST and values
        int restaurantListSize = UpdatedValues.Instance().getRestaurantList().size();
        assertEquals(restaurantListSize, _REST_NUM);
        //Check last and first restaurant's ids
        assertEquals(UpdatedValues.Instance().getRestaurantList().get(0).getId(),
                MockData.getRestaurantList().get(0).getId());
        assertEquals(UpdatedValues.Instance().getRestaurantList().get(restaurantListSize-1).getId(),
                MockData.getRestaurantList().get(_REST_NUM-1).getId());
        //Check size of the MAP
        assertEquals(UpdatedValues.Instance().getRestaurantImageMap().size(), _REST_NUM);
        assertNotNull(UpdatedValues.Instance().getRestaurantImageMap().get(1));
        assertNotNull(UpdatedValues.Instance().getRestaurantImageMap().get(_REST_NUM));

        //2.Clean values
        UpdatedValues.Instance().cleanRestaurants();
        assertEquals(UpdatedValues.Instance().getRestaurantList().size(), 0);
        assertEquals(UpdatedValues.Instance().getRestaurantImageMap().size(), _REST_NUM);

    }

    @Test
    public void updateRestaurantDetails(){
        //1. Add new restaurant details and check it's accessible
        Map<Integer, RestaurantDetail> rDetail = MockData.getRestaurantDetailMap();
        for (int i=1; i<= rDetail.size(); i++)
            UpdatedValues.Instance().addRestaurantDetail(rDetail.get(i));

        Map<Integer, RestaurantDetail> rDetailUpdated = UpdatedValues.Instance().getRestaurantDetailMap();
        assertNotNull(rDetailUpdated);
        assertEquals(rDetailUpdated.size(), _REST_NUM); //size
        assertEquals(rDetailUpdated.get(2).getAddress().toString(),
                        rDetail.get(2).getAddress().toString()); //address
        assertEquals(rDetailUpdated.get(1).getPhone_number(),
                        rDetail.get(1).getPhone_number()); //phone

        //2. Clean restaurant list
        UpdatedValues.Instance().cleanRestaurants();
        rDetailUpdated = UpdatedValues.Instance().getRestaurantDetailMap();
        assertNotNull(rDetailUpdated);
        assertEquals(rDetailUpdated.size(), rDetail.size()); //size


    }
}
