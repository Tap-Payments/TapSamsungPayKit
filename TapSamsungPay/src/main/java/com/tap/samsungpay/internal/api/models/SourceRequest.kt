package com.tap.samsungpay.internal.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by AhlaamK on 7/27/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
data class SourceRequest(
    @SerializedName("id") @Expose var identifier: String
) : Serializable