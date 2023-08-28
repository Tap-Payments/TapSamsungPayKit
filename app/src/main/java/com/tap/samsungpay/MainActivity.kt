package com.tap.samsungpay

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tap.samsungpay.internal.api.responses.Token
import com.tap.samsungpay.open.DataConfiguration
import com.tap.samsungpay.open.SDKDelegate
import com.tap.samsungpay.open.enums.AllowedMethods
import com.tap.samsungpay.open.enums.SDKMode
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    var dataConfig: DataConfiguration = DataConfiguration //** Required**//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeSDK()
        configureSDKData()
    }

    private fun initializeSDK() {
        dataConfig.initSDK(
            this@MainActivity as Context,
            "sk_test_kovrMB0mupFJXfNZWx6Etg5y",
            "company.tap.goSellSDKExample"
        )

    }

    private fun configureSDKData() {
        // pass your activity as a session delegate to listen to SDK internal payment process follow
        dataConfig.addSDKDelegate(object : SDKDelegate {
            override fun onSamsungPayToken(token: String) {

            }

            override fun onTapToken(token: Token) {

            }

            override fun onFailed(error: String) {

            }

        }) //** Required **
        dataConfig.setGatewayId("tappayments")  //**Required GATEWAY ID**/
        dataConfig.setGatewayMerchantID("1124340") //**Required GATEWAY Merchant ID**/
        dataConfig.setAmount(
            BigDecimal.ONE
        ) //**Required Amount**/
        dataConfig.setEnvironmentMode(SDKMode.ENVIRONMENT_TEST) //**Required SDK MODE**/
        /**
         * scope :
         * SMSNung PAY token , Tap Token .
         *
         */
        dataConfig.setTransactionCurrency("USD") //**Required Currency **/
        dataConfig.setCountryCode("US") //**Required Country **/
        dataConfig.setAllowedCardAuthMethods(AllowedMethods.ALL) //**Required type of auth PAN_ONLY, CRYPTOGRAM , ALL**/
        //  dataConfig.setEnvironmentMode(SDKMode.ENVIRONMENT_TEST)
//        settingsManager?.getSDKMode("key_sdkmode")
//            ?.let { dataConfig.setEnvironmentMode(it) } //**Required SDK MODE**/
//
//
    }

}