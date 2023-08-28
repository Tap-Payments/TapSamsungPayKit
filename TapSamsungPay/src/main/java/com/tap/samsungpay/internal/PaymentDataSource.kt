package com.tap.samsungpay.internal

import androidx.annotation.RestrictTo
import com.tap.samsungpay.internal.interfaces.PaymentDataSource
import com.tap.samsungpay.open.enums.AllowedMethods
import com.tap.samsungpay.open.enums.SDKMode
import java.math.BigDecimal
@RestrictTo(RestrictTo.Scope.LIBRARY)
object PaymentDataSource : PaymentDataSource {
    private var currency: String? = null
    private var amount: BigDecimal? = null
    private lateinit var gatewayMerchantId: String
    private lateinit var gatewayId: String
    private lateinit var sdkMode: SDKMode
    private lateinit var allowedMethods: AllowedMethods
   // private lateinit var allowedCardNetworks: ArrayList<String>
    private var allowedCardNetworks: MutableList<String>? = java.util.ArrayList()
    private lateinit var countryCode: String
    /**
     * Set transaction currency.
     *
     * @param transactionCurrency the tap currency
     */
    fun setTransactionCurrency(transactionCurrency: String) {
        currency = transactionCurrency
    }
    /**
     * Set amount.
     *
     * @param amount the amount
     */
    fun setAmount(amount: BigDecimal?) {
        com.tap.samsungpay.internal.PaymentDataSource.amount = amount
    }
    fun setEnvironmentMode(sdkMode: SDKMode) {
        com.tap.samsungpay.internal.PaymentDataSource.sdkMode = sdkMode
    }

    fun setGatewayId(gatewayId: String) {
        com.tap.samsungpay.internal.PaymentDataSource.gatewayId = gatewayId
    }
    fun setGatewayMerchantId(gatewayMerchantId: String) {
        com.tap.samsungpay.internal.PaymentDataSource.gatewayMerchantId = gatewayMerchantId
    }
    fun setAllowedCardNetworks(allowedCardNetworks: MutableList<String>?) {
        com.tap.samsungpay.internal.PaymentDataSource.allowedCardNetworks = allowedCardNetworks
    }

    fun setAllowedCardAuthMethods(allowedMethods: AllowedMethods) {
        com.tap.samsungpay.internal.PaymentDataSource.allowedMethods = allowedMethods
    }

    fun setCountryCode(countryCode: String) {
        com.tap.samsungpay.internal.PaymentDataSource.countryCode = countryCode
    }
//////////////////////////////Getters///////////////////////
    override fun getCurrency(): String? {
        return currency
    }

    override fun getAmount(): BigDecimal? {
        return amount
    }

    override fun getEnvironment(): SDKMode? {
       return sdkMode
    }

    override fun getAllowedCardMethod(): AllowedMethods {
      return allowedMethods
    }

    override fun getAllowedNetworks(): MutableList<String>? {
        return allowedCardNetworks
    }

    override fun getGatewayId(): String {
       return gatewayId
    }

    override fun getGatewayMerchantId(): String {
       return gatewayMerchantId
    }

    override fun getCountryCode(): String {
       return countryCode
    }
}