package com.tap.samsungpay.internal.interfaces

import androidx.annotation.RestrictTo
import com.tap.samsungpay.open.enums.AllowedMethods
import com.tap.samsungpay.open.enums.SDKMode
import java.math.BigDecimal
@RestrictTo(RestrictTo.Scope.LIBRARY)
interface PaymentDataSource {
    /**
     * Transaction currency. @return the currency
     */
    fun getCurrency(): String?

    fun getAmount(): BigDecimal?

    fun getEnvironment(): SDKMode?

    fun getAllowedCardMethod(): AllowedMethods

    fun getAllowedNetworks(): MutableList<String>?

    fun getGatewayId(): String

    fun getGatewayMerchantId(): String

    fun getCountryCode(): String

}