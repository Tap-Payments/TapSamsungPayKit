package com.tap.samsungpay.internal.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.internal.api.enums.URLStatus
import java.io.Serializable

/**
 * Created by AhlaamK on 7/27/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
data class TrackingURL(
    /**
     * The Status.
     */
    @SerializedName("status") @Expose
    var status: URLStatus? = URLStatus.PENDING,

    /**
     * The Url.
     */
    @SerializedName("url")
    @Expose
    var url: String? = null
) : Serializable