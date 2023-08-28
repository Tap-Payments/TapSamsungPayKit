package com.tap.samsungpay

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tap.samsungpay.internal.api.responses.Token
import com.tap.samsungpay.open.DataConfiguration
import com.tap.samsungpay.open.SDKDelegate
import com.tap.samsungpay.open.enums.*
import company.tap.tapcardformkit.open.builder.AuthKey
import company.tap.tapcardformkit.open.builder.PublicKeybuilder.PublicKeyConfiguration
import company.tap.tapcardformkit.open.builder.TapConfiguration
import com.tap.samsungpay.internal.builder.TransactionBuilder.TapCurrency
import com.tap.samsungpay.internal.builder.TransactionBuilder.Transaction
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import company.tap.cardinputwidget.CardBrand
import company.tap.tapcardformkit.open.models.Acceptance
import company.tap.tapcardformkit.open.models.PhoneNumber
import company.tap.tapcardformkit.open.models.TapInterface
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    var dataConfig: DataConfiguration = DataConfiguration //** Required**//
    lateinit var tapConfiguration: TapConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeSDK()
        configureSDKData()

        tapConfiguration =
            TapConfiguration.Builder()
                .setPublicKey(
                    PublicKeyConfiguration.Builder().setSandBoxKey("pk_test_xxxxxxxxxxxxxxxzh")
                        .setProductionKey("test")
                        .build()
                )
                .setEnvironment(SDKMode.ENVIRONMENT_TEST)
                .setMerchant(Merchant.Builder().setId("1124340").setGatwayId("tappayments").build())
                .setTransactions(
                    Transaction.Builder().setAmount(2.4)
                        .setCurrency(TapCurrency("USD")).build()
                )

                .setScope(Scope.TAPTOKEN)
                .setAcceptance(
                    Acceptance(
                        supportedFundSource = SupportedFundSource.DEBIT,
                        supportedBrands = arrayListOf<CardBrand>(
                            CardBrand.AmericanExpress,
                            CardBrand.MasterCard
                        ),
                        supportedPaymentAuthentications = SupportedPaymentAuthentications.ThreeDS
                    )
                )
                .setTapCustomer(getTapCustomer())
                .setTapInterface(
                    TapInterface("en", Edges.CURVED, ThemeMode.LIGHT)

                ).setAuthToken(
                    AuthKey.Builder().setSandBox("sk_test_kovrMB0mupFJXfNZWx6Etg5y")
                        .setProductionLiveKey("sk_test_kovrMB0mupFJXfNZWx6Etg5y").build()
                )
                .setPackageName("company.tap.goSellSDKExample")
                .setDeviceType("Android Native")
                .build()

        TapConfiguration.configureSamsungPayWithTapConfiguration(tapConfiguration,this)


    }

    private fun getTapCustomer(): company.tap.tapcardformkit.open.models.TapCustomer =
        company.tap.tapcardformkit.open.models.TapCustomer(
            identifier = "cus_TS012520211349Za012907577",
            editable = true,
            nameOnCard = "Test",
            emailAddress = "abcd@gmail.com",
            phoneNumber = PhoneNumber("+20", "1066490871"),
            firstName = "Aslm",
            middleName = "middlename",
            lastName = "lastname"
        )

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