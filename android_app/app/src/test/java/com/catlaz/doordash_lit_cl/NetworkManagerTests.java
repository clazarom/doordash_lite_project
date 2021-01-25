package com.catlaz.doordash_lit_cl;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.catlaz.doordash_lit_cl.utils.NetworkStateManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class NetworkManagerTests {

    //NOT MOCKING SPLASH ACTIVITY IN THESE TESTS YET
//    @Before
//    public void setup() {
//        //Mock environment
//        MockitoAnnotations.initMocks(this);
//        Context mockContext = Mockito.mock(Context.class);
//
//        //Intent to start SplashActivity
//       Intent i = new Intent(mockContext, SplashActivity.class);
//        mockContext.startActivity(i);
//        SplashActivity splashActivity = spy(new SplashActivity());
//    }


    @Test
    public void testInternetAvailability(){
        //Mock the connectivity manager
        final ConnectivityManager manager = mock(ConnectivityManager.class);
        final NetworkInfo networkInfo = Mockito.mock(NetworkInfo.class);
        Mockito.when(manager.getActiveNetworkInfo()).thenReturn(networkInfo);

        //Setup network state manager
        NetworkStateManager networkStateManager = new NetworkStateManager(manager);

        //Check network
        //2. Connected & Available = TRUE
        Mockito.when( networkInfo.isAvailable() ).thenReturn(true);
        Mockito.when( networkInfo.isConnected() ).thenReturn(true);
        assertTrue(networkStateManager.isNetworkAvailable());

        //2. Connected & Unavailable = FALL
        Mockito.when( networkInfo.isAvailable() ).thenReturn(false);
        Mockito.when( networkInfo.isConnected() ).thenReturn(true);
        assertFalse(networkStateManager.isNetworkAvailable());

        //2. Disconnected = FALL
        Mockito.when( networkInfo.isConnected() ).thenReturn(false);
        assertFalse(networkStateManager.isNetworkAvailable());

    }

}
