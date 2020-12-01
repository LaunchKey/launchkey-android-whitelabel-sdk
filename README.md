# Multifactor Authentication Mobile Authenticator SDK for Android 

![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/iovation/launchkey-android-authenticator-sdk?label=latest%20release)

  * [Getting Started](#gettingstarted)
  * [Overview](#overview)
  * [Links](#links)
  * [Support](#support)

# <a name="gettingstarted"></a>Getting Started

In order to make this demo application compile and run, make sure to define the following 
resource strings in any xml resource file under the res folder of the project:

```xml
<string name="authenticator_sdk_key">YOUR MOBILE SDK KEY HERE</string>
<string name="authenticator_googlemapsv2_key">YOUR GOOGLE MAPS API V2 ANDROID KEY HERE</string>
```

The Authenticator SDK key is given by the Multifactor Authentication Dashboard, and the Google Maps API v2 
key will allow you to use Google Maps in order to set Geofencing as one of the Security 
Factors. Check the developer documentation in the links below for more information.

# <a name="overview"></a>Overview

Multifactor Authentication is an identity and access management platform. The Authenticator SDK enables 
developers to quickly integrate the Multifactor Authentication platform directly 
in their existing Android applications.

The Multifactor Authentication Mobile Authenticator SDKs are split into separate <b>Core</b> and <b>UI</b> SDKs.
To use them, include the repository in your <i>project</i> level <b>build.gradle</b> file and the dependencies in your <i>app</i> level <b>build.gradle</b> file.
```gradle
repositories {
   maven {
       url "https://github.com/iovation/launchkey-android-authenticator-sdk/raw/master/lk-auth-sdk"
   }
}

dependencies {
    // Core Auth SDK
    implementation 'com.launchkey.android.authenticator.sdk:lk-auth-sdk-core:<version>'

    // (UI) Auth SDK
    implementation 'com.launchkey.android.authenticator.sdk:lk-auth-sdk:<version>'
}
```

Developer documentation for using the Authenticator SDK is found [here](https://docs.launchkey.com/authenticator-sdk/integrate-authenticator-sdk-android-v3.html).

#  <a name="links"></a>Links

  Check our [documentation](https://docs.launchkey.com/authenticator-sdk/before-you-begin.html) for setting up
  a Multifactor Authentication Service and the official [Android Development website](https://d.android.com)
  for everything else regarding Android.

#  <a name="support"></a>Support

## FAQ's

Browse FAQ's or submit a question to the Multifactor Authentication support team for both
technical and non-technical issues. Visit the Multifactor Authentication Support website [here](https://www.iovation.com/contact).
