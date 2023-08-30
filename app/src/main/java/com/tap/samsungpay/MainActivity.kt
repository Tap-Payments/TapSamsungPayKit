package com.tap.samsungpay

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.button.MaterialButton
import com.tap.samsungpay.internal.api.Shipping
import com.tap.samsungpay.internal.api.Tax
import com.tap.samsungpay.internal.api.models.PaymentOption
import com.tap.samsungpay.internal.builder.PublicKeybuilder.Operator
import com.tap.samsungpay.internal.builder.TapConfiguration
import com.tap.samsungpay.internal.builder.TransactionBuilder.Transaction
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.internal.interfaces.PaymentDataSourceImpl
import com.tap.samsungpay.internal.models.*
import com.tap.samsungpay.open.SDKDelegate
import com.tap.samsungpay.open.enums.*
import company.tap.tapcardformkit.open.builder.AuthKey
import company.tap.tapcardvalidator_android.CardBrand


class MainActivity : AppCompatActivity() {

    lateinit var tapConfiguration: TapConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapConfiguration =
            TapConfiguration.Builder()
                .setOperator(
                    Operator.Builder()
                        .setPublicKey("pk_test_Vlk842B1EA7tDN5QbrfGjYzh")
                        .setHashString("test")
                        .build()
                )
                .setMerchant(Merchant.Builder().setId("1124340").setGatwayId("tappayments").build())
                .setTransactions(
                    Transaction.Builder().setAmount(2.4).setCurrency("USD")
                        .setShipping(Shipping("test", 1.0)).setTax(Tax("test", 1.0)).build()
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
                .setFields(Fields(shipping = true, billing = true))
                .setTapCustomer(getTapCustomer())
                .setTapInterface(
                    TapInterface(Language.EN.name, Edges.CURVED, ThemeMode.DARK)

                ).setAuthToken(
                    AuthKey.Builder().setSandBox("sk_test_kovrMB0mupFJXfNZWx6Etg5y")
                        .setProductionLiveKey("sk_test_kovrMB0mupFJXfNZWx6Etg5y").build()
                )
                .setPackageName("company.tap.samsungpay")
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

        Handler(Looper.getMainLooper()).postDelayed({
            Log.e("resp", PaymentDataSourceImpl.paymentOptionsResponse.toString())
            val samsungPayPaymentOption =
                PaymentDataSourceImpl.paymentOptionsResponse?.paymentOptions?.firstOrNull { it.brand == CardBrand.SAMSUNG_PAY }

            applyButtonStyleToSamsungPay(samsungPayPaymentOption)
            // Your Code
        }, 8000)


    }

    fun applyButtonStyleToSamsungPay(samsungPayPaymentOption: PaymentOption?) {
        when (TapConfiguration.getTapConfiguration()?.tapInterface?.theme) {
            ThemeMode.LIGHT -> {

                applyThemeToButton(samsungPayPaymentOption?.buttonStyle?.background?.lightModel?.backgroundColors)
                applyButtonText(
                    getAssetToLoadLink(samsungPayPaymentOption!!,ThemeMode.LIGHT,
                        TapConfiguration.getTapConfiguration()?.tapInterface?.locale.toString()
                    )
                )

            }
            ThemeMode.DARK -> {
                applyThemeToButton(samsungPayPaymentOption?.buttonStyle?.background?.darkModel?.backgroundColors)
                applyButtonText(
                    getAssetToLoadLink(samsungPayPaymentOption!!,ThemeMode.DARK,
                        TapConfiguration.getTapConfiguration()?.tapInterface?.locale.toString()
                    )
                )


            }
            else -> {}
        }

    }

    fun getAssetToLoadLink(
        paymentOption: PaymentOption,
        themeMode: ThemeMode,
        language: String
    ): String {
        return (paymentOption.buttonStyle?.titleAssets?.replace(
            "{theme}",
            themeMode.name.lowercase()
        )?.replace(
            "{lang}",
            language.lowercase()
        )).plus(".png")

    }

    private fun applyButtonText(samsungPayPaymentAsset: String?) {
        Log.e("color", samsungPayPaymentAsset.toString())
        Glide.with(this)
            .asBitmap()
            .load(samsungPayPaymentAsset)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val drawable: Drawable = BitmapDrawable(resources, resource)
                    findViewById<MaterialButton>(R.id.btn_samsung).icon = drawable

                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun applyThemeToButton(buttonColors: ArrayList<String>?) {
        val materialButton = findViewById<MaterialButton>(R.id.btn_samsung)
        var backgroundColors = intArrayOf()
        if (buttonColors?.size!! > 1) {
            var backgroundDrawable: GradientDrawable = GradientDrawable()
            backgroundColors = (buttonColors.map { Color.parseColor(it) }).toIntArray()
            backgroundDrawable.colors = backgroundColors
            backgroundDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            backgroundDrawable = GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT, backgroundColors,
            )
            with(materialButton) {
                background = (backgroundDrawable)
                layoutParams.height = resources.getDimensionPixelSize(R.dimen.fifty)
            }


        } else {
            materialButton.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor(
                    buttonColors[0]
                )
            )

        }


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