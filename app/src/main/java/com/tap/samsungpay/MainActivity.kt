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
import com.chillibits.simplesettings.tool.getPrefStringValue
import com.chillibits.simplesettings.tool.getPrefs
import com.tap.samsungpay.internal.api.responses.Token
import com.tap.samsungpay.internal.builder.publicKeybuilder.Operator
import com.tap.samsungpay.internal.builder.transactionBuilder.OrderDetail
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.internal.models.Acceptance
import com.tap.samsungpay.internal.models.PhoneNumber
import com.tap.samsungpay.internal.models.Shipping
import com.tap.samsungpay.internal.models.TapCustomer
import com.tap.samsungpay.internal.models.TapInterface
import com.tap.samsungpay.internal.models.Tax
import com.tap.samsungpay.open.TapConfiguration
import com.tap.samsungpay.open.TapSamsungPayDelegate
import com.tap.samsungpay.open.enums.ColorStyle
import com.tap.samsungpay.open.enums.Edges
import com.tap.samsungpay.open.enums.Language
import com.tap.samsungpay.open.enums.Scope
import com.tap.samsungpay.open.enums.ThemeMode
import com.tap.tapsamsungpay.R
import java.util.Formatter
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class MainActivity : AppCompatActivity(), TapSamsungPayDelegate {

    private lateinit var tapConfiguration: TapConfiguration
    private var postUrl: String = ""
    private lateinit var hashString: String

    object Hmac {
        fun digest(
            msg: String,
            key: String,
            alg: String = "HmacSHA256"
        ): String {
            val signingKey = SecretKeySpec(key.toByteArray(), alg)
            val mac = Mac.getInstance(alg)
            mac.init(signingKey)

            val bytes = mac.doFinal(msg.toByteArray())
            return format(bytes)
        }

        private fun format(bytes: ByteArray): String {
            val formatter = Formatter()
            bytes.forEach { formatter.format("%02x", it) }
            return formatter.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Required step.
         * Configure SDK with your choice from the given list.
         */
        initConfigurations()
        TapConfiguration.configureSamsungPayWithTapConfiguration(tapConfiguration, this, this)

    }

    private fun initConfigurations() {
        /** Generate HashString**/
        val stringmsg = "x_publickey${
            getPrefStringValue(
                "publicKey",
                "pk_test_OYw82dpQRWGn7tEy6LclFsev"
            )
        }x_amount${
            (getPrefStringValue(
                "amountKey",
                "0.2"
            )).toDouble()
        }x_currency${
            (getPrefStringValue(
                "selectedCurrencyKey",
                "USD"
            ))
        }x_transaction${""}x_post$postUrl"

        hashString = Hmac.digest(
            msg = stringmsg, key = getPrefStringValue(
                "secretKey",
                "sk_test_xliFRQtUrGfMdcCEgO9ohDSw"
            )
        )
        // Log.e("encrypted hashString",hashstring.toString())

        tapConfiguration =
            TapConfiguration.Builder()
                .setOperator(
                    Operator.Builder()
                        .setPublicKey(
                            getPrefStringValue(
                                "publicKey",
                                "pk_test_OYw82dpQRWGn7tEy6LclFsev"
                            )
                        )
                        .setHashString(hashString)
                        .build()
                )//**Required**//
                .setMerchant(
                    Merchant.Builder().setId(getPrefStringValue("merchantIdKey", "1124340"))
                        .setGatwayId(getPrefStringValue("gatewayIdKey", "tappayments")).build()
                )//**Required**//
                .setOrders(
                    OrderDetail.Builder()
                        .setAmount((getPrefStringValue("amountKey", "0.2")).toDouble())
                        .setCurrency((getPrefStringValue("selectedCurrencyKey", "USD")))
                        .setShipping(
                            Shipping(
                                (getPrefStringValue("shipNameKey", "Shipping Test")),
                                (getPrefStringValue("shipAmntKey", "0.1")).toDouble()
                            )
                        ).setTax(
                            Tax(
                                (getPrefStringValue("taxNameKey", "Tax Test")),
                                (getPrefStringValue("shipAmntKey", "0.1")).toDouble()
                            )
                        ) //Optional
                        .setOrderNumber(getPrefStringValue("orderNoKey", "AMZ333")) //**Optional**//
                        .build()
                )
                .setScope(getScope("scopeKey"))
                .setAcceptance(
                    Acceptance(
                        supportedSchemes = getPrefs().getStringSet(
                            "selectedSchemesKey",
                            emptySet<String>()
                        )!!.toMutableList(),
                    )
                )//**Required**//
                .setTapCustomer(getTapCustomer()) //**Required**//
                .setTapInterface(
                    TapInterface(
                        getLanguageMode("selectedLangKey"),
                        getEdges("selectedcardedgeKey"),
                        getThemeMode("selectedthemeKey"), getColorStyle("selectedColorStyleKey")
                    ) //**Optional**//

                )
                .setPackageName(
                    getPrefStringValue(
                        "packageKey",
                        "com.tap.tapsamsungpay"
                    )
                )//**Required**//
                .setServiceId(
                    getPrefStringValue(
                        "serviceIdKey",
                        "1cd18649418d46478eb800"
                    )
                )//**Required**//
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
        return when (getPrefStringValue(key, ThemeMode.DARK.name)) {
            ThemeMode.DARK.name -> ThemeMode.DARK
            ThemeMode.LIGHT.name -> ThemeMode.LIGHT
            else -> ThemeMode.DARK
        }
    }

    private fun getLanguageMode(key: String): String {
        return when (getPrefStringValue(key, Language.EN.name)) {
            Language.EN.name.toLowerCase() -> Language.EN.name
            Language.AR.name.toLowerCase() -> Language.AR.name
            else -> Language.EN.name
        }
    }

    private fun getEdges(key: String): Edges {

        return when (getPrefStringValue(key, Edges.curved.name)) {
            Edges.curved.name -> Edges.curved
            Edges.flat.name -> Edges.flat
            else -> Edges.curved
        }
    }

    private fun getScope(key: String): Scope {

        return when (getPrefStringValue(key, Scope.SAMSUNG_TOKEN.name)) {
            Scope.SAMSUNG_TOKEN.name -> Scope.SAMSUNG_TOKEN
            Scope.TAP_TOKEN.name -> Scope.TAP_TOKEN
            else -> Scope.SAMSUNG_TOKEN
        }
    }

    private fun getColorStyle(key: String): String {

        return when (getPrefStringValue(key, ColorStyle.colored.name)) {
            ColorStyle.colored.name -> {
                ColorStyle.colored.name
            }

            ColorStyle.monochrome.name -> ColorStyle.monochrome.name
            else -> ColorStyle.colored.name
        }
    }

    private fun customAlertBox(title: String, message: String) {
        // Create the object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this)

        // Set the message show for the Alert time
        builder.setMessage(message)

            // Set Alert Title
            .setTitle(title)

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            .setCancelable(false)

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            .setPositiveButton("Yes") {
                // When the user click yes button then app will close
                    dialog, which ->
                dialog.dismiss()
                finish()

            }
             // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            .setNegativeButton("No") {
                // If user click no then dialog box is canceled.
                    dialog, which ->
                dialog.cancel()
                finish()
            }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }

    override fun onError(error: String?) {
        println("error>>" + error)
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
        customAlertBox("onTapToken Called", token.id.toString())

    }

    override fun onCancel(cancel: String) {
        customAlertBox("onCancel Called", cancel)
    }


}