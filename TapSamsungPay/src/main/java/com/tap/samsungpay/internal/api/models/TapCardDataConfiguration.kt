package company.tap.tapcardformkit.internal.api.models

import androidx.annotation.RestrictTo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.internal.api.responses.BaseResponse
import com.tap.samsungpay.open.enums.SDKMODE
import java.io.Serializable

/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
data class TapCardDataConfiguration(
    @SerializedName("authToken") @Expose
    var authToken: String? = null,

    @SerializedName("packageId") @Expose
    var packageId: String? = null,

    @SerializedName("merchantId")
    @Expose
    var merchantId: String? = null,

    @SerializedName("sdkMode")
    @Expose
    val sdkMode: SDKMODE? = SDKMODE.SANDBOX,

    @SerializedName("language")
    @Expose
    val language: String? = "en",

    @SerializedName("selectedCurrency")
    @Expose
    var selectedCurrency: String? = null,

    @SerializedName("selectedCardType")
    @Expose
    var selectedCardType: String? =null,
    @SerializedName("defaultCardHolderName")
    @Expose
    var defaultCardHolderName: String= "CARD HOLDER NAME",
):Serializable, BaseResponse
