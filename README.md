# doordash_lite_caterina
Take home project: a lite version of the DoorDahs app. 
It retrieves store data from DoorDash's server, and displays the restaurants info both in a list and in a map. Additional details are show when clicking on a list element.

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

To build a signed RELEASE version of the application, the files 'keystore_ddlit.jks'--the keystore-- and  'keystore.properties'--passwords and aliases to access the keystore-- need to be download and pasted into the repo independently.

*NOTE: Some locally built files may be modified and already marked by git as modified, since the .gitignore file is not finished

## Project organization

### Android

### Google API project
A paralel project has been created in the Google Cloud Platform to make use of the following Google Services:
- Maps, setup following the [documentation](https://developers.google.com/maps/documentation/android-sdk/config)

A retricted API key has been created for only doordash_lit(e) app to access and request services, as described in the [documentation](https://developers.google.com/maps/documentation/android-sdk/get-api-key)

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


# Testing

*NOTE: InstrumentedTests can only be run with a DEBUG build of the application.

The testing units developed are designed to verify:
- NetworkStateManager - to check if there is Internet connection (used when the app is first launched)
- REST API calls to the server {GET restaurant list , GET restaurant detail}
- The data model (UpdatedValues.class) - to test that inserting, retrieving and consuming data work as expected
- The restaurant listview - both generating mock local values and requesting real store data from the server
- MainActivity navigation - between the PagerViwer fragmetns as well as transitioning between the mainList and detailItem
- SplashActivity - launching, networkstate and notification


## Troubleshooting Testing

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
 


