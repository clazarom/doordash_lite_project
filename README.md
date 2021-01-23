# doordash_lite_caterina
A simple version of the DoorDahs app.

# Getting started

This code has been developed in Windows, with Android Studio IDE. These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Technologies 

### Developing Computer
The specific versions of languages and tools used in these project are:

    - OS: Microsoft Windows 10 HOme
    - Java JRE: "1.8.0_242"
    - Android Studio: 4.1.2
    - Gradle version: 6.5
    - Android Gradle Plugin: 4.1.2
    - Compile SDK version: 29
    - Android SDK:
    	- Target SDK: 29
    	- Minimum SDK: 26


## Download and import
To setup the environment, close/download the GitHub repo. Then, on Android Studio, navigate to import the project:
File > Open > (navigate to)../android_app

*NOTE: Some locally built files may be modified and already marked by git as modified, since the .gitignore file is not finished

## Run the app
After the initial setup, the Android Studio should be ready to build and run - either on a virtual device or a physical android phone.

For an Android phone, make sure the Developer Mode is umblock and USB Debug is allowed when plugin the USB. 

# Functionality

The app, 'DoorDash Lit(e)' uses DoorDahs public API's to request a list of nearby stores, and then display them on a ListView. To do so, there are two main methods implemented:
- Refresh: cleans the previous data and request a new set
- More: request new data and append it a the end of the previous list

Additionally, the user can click on a restaurant/element to open a new screen with additional information.

## Requirements

- Internet: to retrieve the restaurants information, the phone needs a data connection. If not, the app will be stuck in the initial 'Splash' screen.

## Future work

An extra screen has been developed to show the stores in a map -- however, its functionality is still being built

# Testing

The testing units developed are designed to verify:
- The restaurant listview - both generating mock local values and requesting real store data from the server
- MainActivity navigation - between the PagerViwer fragmetns as well as transitioning between the mainList and detailItem
- REST API calls to the server {GET restaurant list , GET restaurant detail}


##Troubleshooting Testing

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
 


