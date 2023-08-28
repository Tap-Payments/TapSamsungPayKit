package com.tap.samsungpay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tap.samsungpay.open.enums.*
import company.tap.tapcardformkit.open.builder.AuthKey
import company.tap.tapcardformkit.open.builder.PublicKeybuilder.PublicKeyConfiguration
import company.tap.tapcardformkit.open.builder.TapConfiguration
import com.tap.samsungpay.internal.builder.TransactionBuilder.TapCurrency
import com.tap.samsungpay.internal.builder.TransactionBuilder.Transaction
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.open.SDKDelegate
import company.tap.tapcardformkit.open.models.Acceptance
import company.tap.tapcardformkit.open.models.PhoneNumber
import company.tap.tapcardformkit.open.models.TapInterface
import company.tap.tapcardvalidator_android.CardBrand

class MainActivity : AppCompatActivity() {
    lateinit var tapConfiguration: TapConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapConfiguration =
            TapConfiguration.Builder()
                .setPublicKey(
                    PublicKeyConfiguration.Builder()
                        .setSandBoxKey("pk_test_Vlk842B1EA7tDN5QbrfGjYzh")
                        .setProductionKey("test")
                        .build()
                )
                .setEnvironment(SDKMODE.SANDBOX)
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
                            CardBrand.americanExpress,
                            CardBrand.masterCard,
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

        TapConfiguration.configureSamsungPayWithTapConfiguration(
            tapConfiguration,
            this,
            object : SDKDelegate {
                override fun onError(error: String?) {
                    Toast.makeText(this@MainActivity, "$error", Toast.LENGTH_SHORT).show()

                }

                override fun onSuccess(token: String) {
                    Toast.makeText(this@MainActivity, "TokenRecieceved", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()

                }

            })


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


}