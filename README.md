# TapSamsungPayKit
A standalone kit for handling SamsungPay

[![Platform](https://img.shields.io/badge/platform-Android-inactive.svg?style=flat)](https://github.com/Tap-Payments/TapSamsungPayKit/)
[![Documentation](https://img.shields.io/badge/documentation-100%25-bright%20green.svg)](https://github.com/Tap-Payments/TapSamsungPayKit/)
[![SDK Version](https://img.shields.io/badge/minSdkVersion-24-blue.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)
[![SDK Version](https://img.shields.io/badge/targetSdkVersion-30-informational.svg)](https://stuff.mit.edu/afs/sipb/project/android/docs/reference/packages.html)



https://github.com/Tap-Payments/TapSamsungPayKit/assets/57221514/459d5389-dde1-4e00-b159-365fe466851e



# Table of Contents
---
1. [Requirements](#requirements)
2. [Installation](#installation)
3. [Setup](#setup)
    1. [TapSamsungPayKit Class Properties](#setup_tapsamsung_pay_sdk_class_properties_secret_key)
    2. [Setup Steps](#setup_steps)
4. [Usage](#usage)
   [Configure SDK with Required Data](#configure_sdk_with_required_data)
5. [TapSamsungPay_Delegate](#sdk_delegate)
    1. [OnTapToken Success Callback](#tap_token_success_callback)
    2. [OnFailure Callback](#failed_callback)
    3. [OnSamsungPay_Token_Success Callback](#samsung_pay_success_callback)
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

### Installation with JitPack
---
[JitPack](https://jitpack.io/) is a novel package repository for JVM and Android projects. It builds Git projects on demand and provides you with ready-to-use artifacts (jar, aar).

To integrate TapSamsungPaySDK into your project add it in your **root** `build.gradle` at the end of repositories:
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

<a name="setup"></a>
# Setup
---
# Get your Tap keys
You can always use the example keys within our example app, but we do recommend you to head to our [onboarding](https://register.tap.company/sell)  page. You will need to register your `package name` to get your `Tap Key` that you will need to activate our `TapSamsungPaySDK`.
<a name="setup_tapsamsung_pay_sdk_class_properties_secret_ke"></a>
### Secret Key and Application ID

To set it up, add the following line of code somewhere in your project and make sure it will be called before any usage of `TapSamsungPaySDK`, otherwise an exception will be thrown. **Required**.

<a name="setup_steps"></a>

## Setup Steps

### With PayButton

For those, who would like to use PayButton.

1. Place *PayButton*.
2. Assign its *TapDataConfiguration* and *TapSamsungPayDelegate*.
3. Implement *TapDataConfiguration* and *TapSamsungPayDelegate*.

### SamsungPay

1. Ask for the CSR from Tap team.
2. From your SamsungPay Developer account:
   a. Go to ServiceManagement 
   
   b. Click on create Service , fill up the details

   c. Once everything is done service will appear as below

   d. Now you will find the service id generated use this to pass to the TapSDK


<a name="setup_tapsamsung_pay_sdk_class_properties_secret_key"></a>
## TapSamsungPaySDK Class Properties
First of all, `TapSamsungPaySDK` should be set up. To set it up, add the following lines of code somewhere in your project and make sure they will be called before any usage of `TapSamsungPaySDK`.

Below is the list of properties in tapGooglePaySDK class you can manipulate. Make sure you do the setup before any usage of the SDK.


*Kotlin:*
Here we need to make a Top level declaration
```kotlin
 private lateinit var tapConfiguration: TapConfiguration
```
   
#Usage
---
<a name="configure_sdk_with_required_data"></a>
### Configure SDK With Required Data

`TapSamsungPaySDK` should be set up. To set it up, add the following lines of code somewhere in your project and make sure they will be called before any usage of `tapGoooglePaySDK`.

*Kotlin:*

```kotlin
/**
         * Required step.
         * Configure SDK with your choice from the given list.
         */
       
        initConfigurations()

   private fun initConfigurations() {
      tapConfiguration =
          TapConfiguration.Builder()
         .setOperator(
            Operator.Builder()
               .setPublicKey(
                  "pk_test_XXXXXXXXXXXXX"
                  
               ) //**Required**/
               .setHashString("test")//**Required**/
               .build()
         )
         .setMerchant(
            Merchant.Builder().setId("Pass MerchantId associated with Tap") //**Required**//
               .setGatwayId("tappayments").build()//**Required has to be tappayments **/
         )
         .setTransactions(
            Transaction.Builder().setAmount(0.1).setCurrency("USD")//**Required to start samsungpayment**/
               .setShipping(Shipping("test", 0.1)).setTax(Tax("test", 0.1)) //Optional
               .build()
         )
         .setScope(Scope.SAMSUNG_TOKEN)//**Required to know what you prefer only samsung token  / tap token  Scope.TAP_TOKEN select from enum**//
         .setAcceptance(
            Acceptance(
               supportedFundSource = SupportedFundSource.DEBIT,
               supportedBrands = arrayListOf<CardBrand>(
                  CardBrand.SAMSUNG_PAY
               ),
               supportedPaymentAuthentications = SupportedPaymentAuthentications.ThreeDS
            )
         )//**Required**/
         .setFields(
            Fields(
               shipping = getPrefBooleanValue("shippingEnableKey", true),//Optional
               billing = getPrefBooleanValue("billingEnableKey", true)//Optional
            )
         )
         .setTapCustomer(getTapCustomer()) //**Required**//
         .setTapInterface(
            TapInterface(
               Language.EN.name,
               Edges.CURVED,
               ThemeMode.DARK
            ) //Optional if not set it will take sdk defaults

         ).setAuthToken(AuthKey.Builder()
            .setSandBox("sk_test_XXXXXXXXXXXX")
            .setProductionLiveKey("sk_live_XXXXXXXXXXXXXXXXXX").build() //**Required by tap**//
         )
         .setPackageName("Pass package id of your app registered with samsung") //**Required**//
         .setDeviceType("Android Native")//**Required**//

         .setServiceId("Pass the service id generated from samsung portal")//**Required to start samsungpayment**//
         .build()

}

```
Pass the above data to TapConfigurations as below:

```kotlin
 TapConfiguration.configureSamsungPayWithTapConfiguration(tapConfiguration, this)
```
<a name="configure_sdk_Session"></a>
## Configure SDK Data
**TapConfiguration** is the main interface for  library from you application
### Properties

<table style="text-align:center">
    <th colspan=1>Property</th>
    <th colspan=1>Type</th>
    <th rowspan=1>Description</th>


   <tr>
	<td> tapSamsunPayDelegate  </td>
	<td> Activity </td>
	<td> Activity. it is used to notify Merchant application with all SDK Events </td>
   <tr>


</table>

### Methods

<table style="text-align:center">
    <th colspan=1>Property</th>
    <th colspan=1>Type</th>
      <tr>
   	 <td> setCurrency  </td>
   	 <td> Set the transaction currency associated to your account.  currency must be of type TapCurrency("currency_iso_code"). i.e new TapCurrency("USD") </td>
    </tr>
    <tr>
	 <td> setEnvironmentMode  </td>
	 <td> SDK offers different environment modes such as [ TEST - PRODUCTION]   </td>
    </tr>
    <tr>
	 <td> setAmount </td>
	 <td> Set Total Amount. Amount value must be of type BigDecimal i.e new BigDecimal(40) </td>
    </tr>
    <tr>
	 <td> setGatewayId </td>
	 <td> Gateway id required to use TAP as PSP . Here it is tappayments</td>
    </tr>
    <tr>
  	 <td> setGatewayMerchantID </td>
  	 <td> MerchantID available with TAP</td>
 </tr>

</table>
<a name="sdk_open_interfaces"></a>
### SDK Open Interfaces
 
SDK open Interfaces available for implementation through Merchant Project:

1. TapSamsunPayDelegate
```kotlin
     fun onError(error: String?)
     fun onSamsungPayToken(token: String)
     fun onReady(readyStatus: String)
     fun onTapToken(token: Token)
     fun onCancel()
```
<a name="sdk_open_enums"></a>
## SDK Open ENUMs
SDK open Enums available for implementation through Merchant Project:

```kotlin
enum class Scope {
    TAP_TOKEN,
    SAMSUNG_TOKEN
}




enum class ThemeMode {
    DARK,
    LIGHT,
}
enum class Language {
    EN,
    AR,
}


enum class SupportedBrands {
    AMERICAN_EXPRESS,
    MADA,
    MASTERCARD,
    OMANNET,
    VISA,
    MEEZA,
    ALL
}

enum class SupportedFundSource {
    ALL,
    DEBIT,
    CREDIT
}

enum class SupportedPaymentAuthentications {
    ThreeDS,
    EMV,
}

enum class SDKMODE {
    SANDBOX,
    PRODUCTION,
}


enum class Edges {
    CURVED,
    STRAIGHT
}
```
## SDK Delegate

**SDK Delegate** is an interface which you may want to implement to receive payment/authorization/card saving status updates and update your user interface accordingly when payment window closes.
Below are listed down all available callbacks:

<a name="samsung_pay_success_callback"></a>
### SamsungPay Token Success Callback

Notifies the receiver that samsungpay token has succeed.

#### Declaration
*Kotlin:*
```kotlin
-  fun onSamsungPayToken(token:String)
```
#### Arguments

**token**: Successful Token object.

<a name="tap_token_success_callback"></a>
### TAP Token Success Callback

Notifies the receiver that token generated for TAP .
#### Declaration

*Kotlin:*
```kotlin
-  fun onTapToken(token: Token)
```

#### Arguments

**token**: Token object from TAP.

<a name=failed_callback"></a>
### Error Callback

Notifies the receiver that failed.
#### Declaration

*Kotlin:*

```kotlin
-   fun onError(error: String?)
```

#### Arguments
**error**: Failure object.

<a name="onready_success_callback"></a>
### Ready Callback

Notifies the receiver that failed.
#### Declaration

*Kotlin:*

```kotlin
-   fun onReady(readyStatus: String)
```
