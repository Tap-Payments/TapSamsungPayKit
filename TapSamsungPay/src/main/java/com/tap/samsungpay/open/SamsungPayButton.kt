package com.tap.samsungpay.open

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.button.MaterialButton
import com.tap.samsungpay.internal.api.models.PaymentOption
import com.tap.samsungpay.internal.builder.TapConfiguration
import com.tap.samsungpay.open.enums.ThemeMode
import com.tap.tapsamsungpay.R


@SuppressLint("ViewConstructor")
class SamsungPayButton : LinearLayout {
    val buttonSamsung by lazy { findViewById<MaterialButton>(R.id.btn_samsung) }

    /**
     * Simple constructor to use when creating a TapPayCardSwitch from code.
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     **/
    constructor(context: Context) : super(context)

    /**
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     *  @param attrs The attributes of the XML Button tag being used to inflate the view.
     *
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        View.inflate(context, R.layout.samsung_pay_layout, this)
    }

    fun applyStyleToSamsungButton(samsungPayPaymentOption: PaymentOption?) {

        when (TapConfiguration.getTapConfiguration()?.tapInterface?.theme) {
            ThemeMode.LIGHT -> {

                applyThemeToButton(samsungPayPaymentOption?.buttonStyle?.background?.lightModel?.backgroundColors)
                applyButtonText(
                    getAssetToLoadLink(
                        samsungPayPaymentOption!!, ThemeMode.LIGHT,
                        TapConfiguration.getTapConfiguration()?.tapInterface?.locale.toString()
                    )
                )

            }
            ThemeMode.DARK -> {
                applyThemeToButton(samsungPayPaymentOption?.buttonStyle?.background?.darkModel?.backgroundColors)
                applyButtonText(
                    getAssetToLoadLink(
                        samsungPayPaymentOption!!, ThemeMode.DARK,
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
        Glide.with(this)
            .asBitmap()
            .load(samsungPayPaymentAsset)
            .apply( RequestOptions().override(50, 50))

            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val drawable: Drawable = BitmapDrawable(resources, resource)
                    buttonSamsung.icon = drawable

                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun applyThemeToButton(buttonColors: ArrayList<String>?) {
        var backgroundColors = intArrayOf()
        if (buttonColors?.size!! > 1) {
            var backgroundDrawable: GradientDrawable = GradientDrawable()
            backgroundColors = (buttonColors.map { Color.parseColor(it) }).toIntArray()
            backgroundDrawable.colors = backgroundColors
            backgroundDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
            backgroundDrawable = GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT, backgroundColors,
            )
            backgroundDrawable.cornerRadius = 30f
            with(buttonSamsung) {
                background = (backgroundDrawable)
                layoutParams.height = resources.getDimensionPixelSize(com.tap.tapsamsungpay.R.dimen.fifty)
            }


        } else {
            buttonSamsung.backgroundTintList = ColorStateList.valueOf(
                Color.parseColor(
                    buttonColors[0]
                )
            )

        }


    }

}