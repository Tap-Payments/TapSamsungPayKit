package com.tap.samsungpay.internal.api.enums

import androidx.annotation.RestrictTo
import com.google.gson.annotations.SerializedName

/**
 * Created by AhlaamK on 7/24/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/

/**
 *
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
enum class PaymentType(val paymentType: String) {
    /**
     * Card payment type.
     */
    @SerializedName("card")
    CARD("card"),
    @SerializedName("device")
    DEVICE("card"),
    /**
     * Web payment type.
     */
    @SerializedName("web")
    WEB("web"),
    /**
     * Saved card payment type.
     */
    @SerializedName("savedCard")
    SavedCard("savedCard"),
    telecom ("telecom"),
    @SerializedName("all")
    ALL("all"),

}