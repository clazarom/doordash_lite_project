doordash_lite_caterina

* Testings

** Possible causes for tests failing | Troubleshooting

1) Test fails with "NoActivityResumedException" error
Espresso activity test will fail when the phone's screen has been turn off AND the phone is locked.

To solve it, either make sure the screen is ON or disable lock screen (so that, if the screen goes off there is no lock

2) PerformException: Error performing 'single click - At Coordinates: 539, 1569 and precision: 16, 16' on view 'Animations or transitions are enabled on the target device.

During Espresso tests, it may happen that, when anitmations or transitions are enabled, due to the extra lag the introduce, the "testLoadRestaurants" may fail -- due to the Thread.sleep used to wait for the list to be downloaded.

To fix:
Disable animations to improve performance: 
- Go to Settings > System > Advanced > Developer Options > (scroll down to Drawing section) Window animation scale, Transition animation scale, and Animator duration scale.
- Tap to turn off/speed up all animations options {Window animation scale, Transition animation scale, Animation duration scale}
}

3) PerformException: Error performing 'load adapter data' 
On "testLoadRestaurants", the restaurants will not load fast enough... causing the test to fail with a PerformException

4) Never ending test5RestaurantDetailAndNavigation
If the listview is never filled with values, this test will continue to run (infinite loop)
 


