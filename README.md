# TapSamsungPayKit
A standalone kit for handling SamsungPay

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/Tap-Payments/TapSamsungPayKit/)
[![Documentation](https://img.shields.io/badge/documentation-100%25-bright%20green.svg)](https://github.com/Tap-Payments/TapSamsungPayKit/)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-21-blue.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-30-informational.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)



# Table of Contents
---
1. [Requirements](#requirements)
2. [Installation](#installation)
    1. [Include TapSamsungPayKit library as a dependency module in your project](#include_library_to_code_locally)
    2. [Installation with jitpack](#installation_with_jitpack)

3. [Setup](#setup)
    1. [TapSamsungPayKit Class Properties](#setup_tapsamsung_pay_sdk_class_properties_secret_key)
    2. [TapSamsungPayKit Button](#setup_tapsamsung_pay_button)
4. [Usage](#usage)
    1. [Configure SDK with Required Data](#configure_sdk_with_required_data)
    2. [Configure SDK Look and Feel](#configure_sdk_look_and_feel)


5. [SDKSession Delegate](#sdk_delegate)
    1. [OnTapToken Success Callback](#payment_success_callback)
    2. [OnFailure Callback](#payment_failure_callback)
    3. [OnSamsungPay_Token_Success Callback](#authorization_success_callback)
   4.  [OnReady Callback](#onready_success_callback)

6. [Additional_Configuration_ GooglePay](#additional_config_googlepay)

7. [Documentation](#docs)

<a name="requirements"></a>
# Requirements
---
To use the SDK the following requirements must be met:

1. **Android Studio 2022.3.1** or newer
2. **Android SDK Tools 31** or newer
3. **Android Platform Version: API 33: Android 10  revision 7** or later
4. **Android targetSdkVersion: 33

<a name="installation"></a>
<a name="include_library_to_code_locally"></a>
### Include TapSamsungPay library as a dependency module in your project
---
1. Clone checkoutSDK library from Tap repository
   ```
       https://github.com/Tap-Payments/TapSamsungPayKit
    ```
2. Add goSellSDK library to your project settings.gradle file as following
    ```java
        include ':library', ':YourAppName'
    ```
3. Setup your project to include checkout as a dependency Module.
    1. File -> Project Structure -> Modules -> << your project name >>
    2. Dependencies -> click on **+** icon in the screen bottom -> add Module Dependency
    3. select checkout library

<a name="installation_with_jitpack"></a>
### Installation with JitPack
---
[JitPack](https://jitpack.io/) is a novel package repository for JVM and Android projects. It builds Git projects on demand and provides you with ready-to-use artifacts (jar, aar).

To integrate tapGooglePay™SDK into your project add it in your **root** `build.gradle` at the end of repositories:
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```java
	dependencies {
	        implementation 'com.github.Tap-Payments:TapSamsungPayKit:0.0.'
	}
```