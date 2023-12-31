package com.tap.samsungpay.internal.api.responses

import androidx.annotation.NonNull
import androidx.annotation.RestrictTo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.internal.api.enums.LogsModel
import com.tap.samsungpay.internal.api.enums.Permission
import com.tap.samsungpay.internal.api.models.AssetsModel
import java.io.Serializable
import java.util.ArrayList

/**
 * Created by AhlaamK on 3/30/23.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
data class InitResponseModel(@SerializedName("payment_options")
                             @Expose
                             @NonNull var paymentOptionsResponse: PaymentOptionsResponse,
                             @SerializedName("status")
                             @Expose
                             @NonNull val status: String,
                             @SerializedName("merchant")
                             @Expose
                             @NonNull val merchant: MerchantData,
                             @SerializedName("assests")
                             @Expose
                             @NonNull var assestsModel: AssetsModel,
                             @SerializedName("session")
                             @Expose
                             @NonNull val session: String,):Serializable
data class MerchantData(

    @SerializedName("encryption_key")
    @Expose
    val encryptionKey: String? = null,

    @SerializedName("country_code")
    @Expose
    val countryCode: String? = null,

    @SerializedName("logo")
    @Expose
    val logo: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("sdk_settings")
    @Expose
    val sdkSettings: SDKSettingsData? = null,
    @SerializedName("permissions")
    @Expose
    val permissions: ArrayList<Permission>? = null,
    @SerializedName("live_mode")
    @Expose
    val live_mode: Boolean? = false,
    @SerializedName("livemode")
    @Expose
    val liveMode: Boolean? = false,
    @SerializedName("verified_application")
    @Expose
    val verifiedApplication: Boolean? = false,
    @SerializedName("device_id")
    @Expose
    val deviceId: String? = null,
    @SerializedName("background")
    @Expose
    val backgroundModel: BackgroundModel? = null,

    @SerializedName("logs")
    @Expose
    val logs: ArrayList<LogsModel>? = null,

    ): Serializable

data class SDKSettingsData(
    @SerializedName("status_display_duration")
    var statusDisplayDuration:Int,
    @SerializedName("otp_resend_interval")
    @Expose
    var otpResendInterval: Int,
    @SerializedName("otp_resend_attempts")
    @Expose
    var otpResendAttempts: Int,
) : Serializable


data class BackgroundModel(
    @SerializedName("url")
    @Expose
    var urlBackground:String,

    @SerializedName("mode")
    @Expose
    var mode: String,

    @SerializedName("color")
    @Expose
    var color: ColorModel,
) : Serializable

data class ColorModel(
    @SerializedName("dark")
    @Expose
    var dark: DarkModel,

    @SerializedName("light")
    @Expose
    var light: LightModel
) : Serializable

data class DarkModel(
    @SerializedName("color")
    @Expose
    var color:String,

    @SerializedName("image")
    @Expose
    var image: String
) : Serializable

data class LightModel(
    @SerializedName("color")
    @Expose
    var color:String,

    @SerializedName("image")
    @Expose
    var image: String
) : Serializable
