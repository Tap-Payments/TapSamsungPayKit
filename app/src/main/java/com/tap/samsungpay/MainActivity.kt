package com.tap.samsungpay

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chillibits.simplesettings.tool.getPrefBooleanValue
import com.chillibits.simplesettings.tool.getPrefStringValue
import com.tap.samsungpay.internal.api.Shipping
import com.tap.samsungpay.internal.api.Tax
import com.tap.samsungpay.internal.builder.PublicKeybuilder.Operator
import com.tap.samsungpay.internal.builder.TapConfiguration
import com.tap.samsungpay.internal.builder.TransactionBuilder.Transaction
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.internal.models.*
import com.tap.samsungpay.open.SDKDelegate
import com.tap.samsungpay.open.enums.*
import company.tap.tapcardformkit.open.builder.AuthKey
import company.tap.tapcardvalidator_android.CardBrand


class MainActivity : AppCompatActivity() {

    lateinit var tapConfiguration: TapConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tapConfiguration =
            TapConfiguration.Builder()
                .setOperator(
                    Operator.Builder()
                        .setPublicKey(getPrefStringValue("publicKey","pk_test_Vlk842B1EA7tDN5QbrfGjYzh"))
                        .setHashString(getPrefStringValue("hashKey","test"))
                        .build()
                )
                .setMerchant(Merchant.Builder().setId(getPrefStringValue("merchantIdKey","1124340")).setGatwayId(getPrefStringValue("gatewayIdKey","tappayments")).build())
                .setTransactions(
                    Transaction.Builder().setAmount(0.1).setCurrency("USD")
                        .setShipping(Shipping("test", 0.1)).setTax(Tax("test", 0.1)).build()
                )
                .setScope(Scope.TAPTOKEN)
                .setAcceptance(
                    Acceptance(
                        supportedFundSource = SupportedFundSource.DEBIT,
                        supportedBrands = arrayListOf<CardBrand>(
                            CardBrand.SAMSUNG_PAY
                        ),
                        supportedPaymentAuthentications = SupportedPaymentAuthentications.ThreeDS
                    )
                )
                .setFields(Fields(shipping = getPrefBooleanValue("shippingEnableKey",true), billing = getPrefBooleanValue("billingEnableKey",true)))
                .setTapCustomer(getTapCustomer())
                .setTapInterface(
                    TapInterface(Language.EN.name, Edges.CURVED, ThemeMode.DARK)

                ).setAuthToken(
                    AuthKey.Builder().setSandBox("sk_test_kovrMB0mupFJXfNZWx6Etg5y")
                        .setProductionLiveKey("sk_test_kovrMB0mupFJXfNZWx6Etg5y").build()
                )
                .setPackageName(getPrefStringValue("packageKey","company.tap.samsungpay"))
                .setDeviceType(getPrefStringValue("deviceTypeKey","Android Native"))
                .build()

        TapConfiguration.configureSamsungPayWithTapConfiguration(
            tapConfiguration,
            this,
            object : SDKDelegate {
                override fun onError(error: String?) {
                //    Toast.makeText(this@MainActivity, "$error", Toast.LENGTH_SHORT).show()

                }

                override fun onSuccess(token: String) {
                    println("onSuccess the token>>"+token)
                    Toast.makeText(this@MainActivity, "TokenRecieceved", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                //    Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()

                }

            })

        setContentView(R.layout.activity_main)


    }


    private fun getTapCustomer(): TapCustomer =
        TapCustomer(
            identifier = "cus_TS012520211349Za012907577",
            editable = true,
            emailAddress = "abcd@gmail.com",
            phoneNumber = PhoneNumber("+20", "1066490871"),
            firstName = "Aslm",
            middleName = "middlename",
            lastName = "lastname"
        )


}