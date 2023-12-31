package com.tap.samsungpay.internal.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by AhlaamK on 6/14/21.

Copyright (c) 2021    Tap Payments.
All rights reserved.
 **/
data class TransactionDetails(
    @SerializedName("created") @Expose
        var created: Long = 0,

    @SerializedName("timezone")
        @Expose
        val timezone: String,

    @SerializedName("authorization_id")
        @Expose
        val authorizationID: String? = null,

    @SerializedName("url")
        @Expose
        val url: String? = null,

    @SerializedName("order")
        @Expose
        val order: Order? = null,

    @SerializedName("expiry")
        @Expose
        val expiry: Expiry? = null,

    @SerializedName("asynchronous")
        @Expose
        val asynchronous: Boolean = false,

    @SerializedName("amount") @Expose
        var amount: Double = 0.0,

    @SerializedName("currency") @Expose
        private var currency: String? = null
) : Serializable
