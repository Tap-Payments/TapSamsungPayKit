package company.tap.tapcardformkit.internal.api.models

import android.util.Log
import androidx.annotation.RestrictTo
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.internal.PaymentDataSource
import com.tap.samsungpay.internal.interfaces.PaymentDataSourceImpl
import company.tap.tapnetworkkit.utils.CryptoUtil

import java.io.Serializable

/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
class CreateTokenCard constructor(
    cardNumber: String,
    expirationMonth: String,
    expirationYear: String,
    cvc: String,
    cardholderName: String?,
    address: Address?
) : Serializable {

    @SerializedName("crypted_data")
    @Expose
    var sensitiveCardData: String? = null

    @SerializedName("address")
    @Expose
    var address: Address? = null


    class SensitiveCardData internal constructor(
        @field:Expose @field:SerializedName("number") private val number: String,
        @field:Expose @field:SerializedName(
            "exp_year"
        ) private val expirationYear: String,
        @field:Expose @field:SerializedName("exp_month") private val expirationMonth: String,
        @field:Expose @field:SerializedName(
            "cvc"
        ) private val cvc: String,
        @field:Expose @field:SerializedName("name") private val cardholderName: String?
    )

    init {
        val _sensitiveCardData =
            SensitiveCardData(cardNumber, expirationYear, expirationMonth, cvc, cardholderName)
        val cryptedDataJson = Gson().toJson(_sensitiveCardData)
        Log.e("sensitiveData", cryptedDataJson.toString())
        Log.e("sensitiveData EncryptionKey", PaymentDataSourceImpl.getMerchantData()?.encryptionKey.toString())

//        this.sensitiveCardData = PaymentDataSourceImpl.getMerchantData()?.encryptionKey?.let {
//            CryptoUtil.encryptJsonString(
//                cryptedDataJson,
//                it
//            )
//        }
        this.address = address
    }
}
