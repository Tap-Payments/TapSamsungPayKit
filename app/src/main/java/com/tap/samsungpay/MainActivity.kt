/**
 *   Created by AhlaamK on 10/19/23, 12:33 PM
 *   Copyright (c) 2023 .
 *   Tap Payments All rights reserved.
 *
 */

package com.tap.samsungpay

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.simplesettings.tool.getPrefBooleanValue
import com.chillibits.simplesettings.tool.getPrefStringValue
import com.chillibits.simplesettings.tool.getPrefs
import com.tap.samsungpay.internal.api.responses.Token
import com.tap.samsungpay.internal.builder.PublicKeybuilder.Operator
import com.tap.samsungpay.internal.builder.TransactionBuilder.Transaction
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.internal.models.Acceptance
import com.tap.samsungpay.internal.models.Fields
import com.tap.samsungpay.internal.models.PhoneNumber
import com.tap.samsungpay.internal.models.Shipping
import com.tap.samsungpay.internal.models.TapCustomer
import com.tap.samsungpay.internal.models.TapInterface
import com.tap.samsungpay.internal.models.Tax
import com.tap.samsungpay.open.TapConfiguration
import com.tap.samsungpay.open.TapSamsunPayDelegate
import com.tap.samsungpay.open.enums.Edges
import com.tap.samsungpay.open.enums.Language
import com.tap.samsungpay.open.enums.Scope
import com.tap.samsungpay.open.enums.SupportedFundSource
import com.tap.samsungpay.open.enums.SupportedPaymentAuthentications
import com.tap.samsungpay.open.enums.ThemeMode
import company.tap.tapcardformkit.open.builder.AuthKey


class MainActivity : AppCompatActivity() , TapSamsunPayDelegate{

    private lateinit var tapConfiguration: TapConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Required step.
         * Configure SDK with your choice from the given list.
         */
        initConfigurations()
        TapConfiguration.configureSamsungPayWithTapConfiguration(tapConfiguration, this)

    }

    private fun initConfigurations() {

        tapConfiguration =
            TapConfiguration.Builder()
                .setOperator(
                    Operator.Builder()
                        .setPublicKey(
                            getPrefStringValue(
                                "publicKey",
                                "pk_test_Vlk842B1EA7tDN5QbrfGjYzh"
                            )
                        )
                        .setHashString(getPrefStringValue("hashKey", "test"))
                        .build()
                )
                .setMerchant(
                    Merchant.Builder().setId(getPrefStringValue("merchantIdKey", "1124340"))
                        .setGatwayId(getPrefStringValue("gatewayIdKey", "tappayments")).build()
                )
                .setTransactions(
                    Transaction.Builder().setAmount((getPrefStringValue("amountKey", "0.1")).toDouble()).setCurrency((getPrefStringValue("selectedCurrencyKey", "USD")))
                        .setShipping(Shipping((getPrefStringValue("shipNameKey", "tester")), (getPrefStringValue("shipAmntKey", "0.1")).toDouble())).setTax(Tax((getPrefStringValue("taxNameKey", "test")),  (getPrefStringValue("shipAmntKey", "0.1")).toDouble())) //Optional
                        .build()
                )
                .setScope(getScope("scopeKey"))
                .setAcceptance(
                    Acceptance(
                        supportedFundSource = SupportedFundSource.DEBIT,
                        supportedBrands = getPrefs().getStringSet("selectedBrandsKey", emptySet<String>())!!.toMutableList(),
                        supportedPaymentAuthentications = SupportedPaymentAuthentications.ThreeDS
                    )
                )
                .setFields(
                    Fields(
                        shipping = getPrefBooleanValue("shippingEnableKey", true),//Optional
                        billing = getPrefBooleanValue("billingEnableKey", true)//Optional
                    )
                )
                .setTapCustomer(getTapCustomer()) //Required
                .setTapInterface(
                    TapInterface(
                        getLanguageMode("selectedlangKey"),
                        Edges.CURVED,
                        getThemeMode("selectedthemeKey")
                    ) //Optional

                ).setAuthToken(AuthKey.Builder()
                    .setSandBox(getPrefStringValue("sandboxKey","sk_test_kovrMB0mupFJXfNZWx6Etg5y"))
                     .setProductionLiveKey(getPrefStringValue("productionKey","sk_test_kovrMB0mupFJXfNZWx6Etg5y")).build()
                )
                .setPackageName(getPrefStringValue("packageKey", "company.tap.samsungpay"))
                .setDeviceType(getPrefStringValue("deviceTypeKey", "Android Native"))

                .setServiceId(getPrefStringValue("serviceIdKey", "fff80d901c2849ba8f3641"))
                .setServiceId(getPrefStringValue("serviceIdKey", "fff80d901c2849ba8f3641"))
                .setOrderNumber(getPrefStringValue("orderNoKey", "AMZ333")) //**Optional**//
                .build()

    }


    private fun getTapCustomer(): TapCustomer =
        TapCustomer(
            identifier = "cus_TS012520211349Za012907577",
            editable = true,
            emailAddress = "abcd@gmail.com",
            phoneNumber = PhoneNumber("965", "66175090"),
            firstName = "FirstName",
            middleName = "middlename",
            lastName = "lastname"
        )


    private fun getThemeMode(key: String): ThemeMode? {
        val selecetdTheme: String = getPrefStringValue(key, ThemeMode.DARK.name)

        if (selecetdTheme == ThemeMode.DARK.name) return ThemeMode.DARK
        else if (selecetdTheme == ThemeMode.LIGHT.name) return ThemeMode.LIGHT
        else return ThemeMode.DARK
    }

    private fun getLanguageMode(key: String): String {
        val selectedlangKey: String = getPrefStringValue(key, Language.EN.name)

        if (selectedlangKey == Language.EN.name) return Language.EN.name
        else if (selectedlangKey == Language.AR.name) return Language.AR.name
        else return Language.EN.name
    }

    private fun getScope(key: String): Scope {
        val scopeKey: String = getPrefStringValue(key, Scope.SAMSUNG_TOKEN.name)

        println("Scope.TAP_TOKEN.name"+Scope.TAP_TOKEN.name)
        if (scopeKey == Scope.SAMSUNG_TOKEN.name) return Scope.SAMSUNG_TOKEN
        else if (scopeKey == Scope.TAP_TOKEN.name) return Scope.TAP_TOKEN
        else return Scope.SAMSUNG_TOKEN
    }

    private fun customAlertBox(title: String, message: String) {
        // Create the object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this)

        // Set the message show for the Alert time
        builder.setMessage(message)

        // Set Alert Title
        builder.setTitle(title)

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes") {
            // When the user click yes button then app will close
                dialog, which ->
            dialog.dismiss()

        }

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No") {
            // If user click no then dialog box is canceled.
                dialog, which ->
            dialog.cancel()
        }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }

    override fun onError(error: String?) {
        if (error != null) {
            customAlertBox("error", error)
        }

    }

    override fun onSamsungPayToken(token: String) {
        println("onSamsungPayToken the token>>" + token)
        customAlertBox("onSamsungPayToken", token)


    }

    override fun onReady(readyStatus: String) {
        println("onReady>>" + readyStatus)
    }

    override fun onTapToken(token: Token) {
        customAlertBox("onTapToken", token.id.toString())
    }

    override fun onCancel() {
        //    Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()

    }



}