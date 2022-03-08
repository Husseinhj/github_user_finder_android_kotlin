# Dependencies
The following section describes the dependencies used and explains each of them in detail.


## Processing
In the search section, we use *coroutine* libraries to handle processes and jobs.

```groovy
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0-native-mt'
```

## IO Service
We choose to use Retrofit2 library to connect to API's, as it is handy and provides features we were looking for, such as interception and modeling a response to a class we choose.

```groovy
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.5'
```

As a future improvement, we can handle refresh-tokens or access-tokens for API use or analyzing errors by utilizing the *logging-interceptor* library before making requests or after getting responses.

The most important aspect of this project is handling the caching of all downloaded images. *Glide* allows us to download and cache images.
```groovy
implementation 'com.github.bumptech.glide:glide:4.13.1'
```

## UI
For UIs, we used the Android default components.
Our main reasons for choosing Android's navigation library were:
Support for storing fragment states and restoring them
Multi-back stack support for fragments
- Gives us a better overview of the pages we used

All these essential features were released in an alpha version and have recently been fixed by Google.

```groovy
def nav_version = "2.5.0-alpha03"

implementation 'androidx.core:core-ktx:1.7.0'
implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
implementation 'com.google.android.material:material:1.5.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
implementation "androidx.lifecycle:lifecycle-viewmodel-savedstate:$nav_version"
```