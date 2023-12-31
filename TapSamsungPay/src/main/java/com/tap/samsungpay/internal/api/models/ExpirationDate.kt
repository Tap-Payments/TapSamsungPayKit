package com.tap.samsungpay.internal.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by AhlaamK on 6/14/21.

Copyright (c) 2021    Tap Payments.
All rights reserved.
 **/
data class ExpirationDate(
    @SerializedName("month")
    @Expose
     var month: String,

    @SerializedName("year")
    @Expose
     val year: String
) : Serializable
