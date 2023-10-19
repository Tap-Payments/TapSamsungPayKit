package com.tap.samsungpay.internal.interfaces


import androidx.annotation.RestrictTo
import com.tap.samsungpay.internal.*
import com.tap.samsungpay.internal.api.*
import com.tap.samsungpay.internal.api.models.BINLookupResponse
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import company.tap.checkout.open.models.Destinations
import com.tap.samsungpay.internal.api.responses.InitResponseModel
import com.tap.samsungpay.internal.api.responses.MerchantData
import com.tap.samsungpay.internal.api.enums.TransactionMode
import company.tap.tapcardformkit.internal.api.models.*
import com.tap.samsungpay.internal.api.responses.PaymentOptionsResponse
import com.tap.samsungpay.internal.models.TapCustomer
import com.tap.samsungpay.open.enums.SDKMODE

import java.math.BigDecimal

/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
object PaymentDataSourceImpl : PaymentDataSource {

    private var sdkMode: SDKMODE = SDKMODE.SANDBOX
    private var merchantData: MerchantData? = null
    private var merchant: Merchant? = null

    private var tokenConfig: String? = null
    private var authKeys: String? = null
    private var initResponseModel: InitResponseModel? = null
    private var transactionMode: TransactionMode? = null
    private var binLookupResponse: BINLookupResponse? = null
    private var cardType: String? = null
    private var selectedCurrency: String? = null
    private var _defaultCardHolderName: String = "CARD HOLDER NAME"
    private lateinit var paymentMetadata: java.util.HashMap<String, String>
    private val paymentStatementDescriptor: String? = null
    private var requires3DSecure = false
    private var postURL: String? = null
    private var paymentDescription: String? = null
    private lateinit var tapCustomer: TapCustomer
    private val cardIssuer: CardIssuer? = null
    var paymentOptionsResponse: PaymentOptionsResponse? = null
    private var selectedAmount: BigDecimal? = null
    private var topup: TopUp? = null

    private var orderObject: OrderObject? = null
    private var currency: TapCurrency? = null
    private var amount: BigDecimal? = null
    private var taxes: ArrayList<Tax>? = null
    private var destination: List<Destinations>? = null
    private var supportedPaymentMethods: List<String>? = null
    private var supportedCurrencies: List<String>? = null

    private var paymentType: String? = null


    /**
     * Set sdkSettings.
     *
     * @param sdkSettings the sdkSettings
     */
    fun setMerchantData(merchantData: MerchantData?) {
        this.merchantData = merchantData
    }

    fun setMerchant(merchant: com.tap.samsungpay.internal.builder.merchantBuilder.Merchant) {
        this.merchant = merchant
    }


    fun setDestination(destination: List<Destinations>?) {
        this.destination = destination
    }

    fun setSupportedPaymentMethods(supportedPaymentMethods: List<String>?) {
        this.supportedPaymentMethods = supportedPaymentMethods
    }
    fun setSupportedCurrencies(supportedCurrencies: List<String>?) {
        this.supportedCurrencies = supportedCurrencies
    }

    fun getSupportedCurrencies(): List<String>? {
        return this.supportedCurrencies
    }
    fun getSupportedPaymentMethods(): List<String>? {
        return this.supportedPaymentMethods
    }

    fun setCardType(cardType: String?) {
        this.cardType = cardType
    }

    fun setPaymentType(paymentType: String?) {
        this.paymentType = paymentType
    }


    /**
     * Set sdkMode.
     *
     * @param sdkMode the sdkMode
     */
    fun setSDKMode(sdkMode: SDKMODE) {
        this.sdkMode = sdkMode
    }

    /**
     * Set defaultCardHolderName.
     *
     * @param defaultCardHolderName the defaultCardHolderName
     */
    fun setDefaultCardHolderName(defaultCardHolderName: String) {
        println("setDefaultCardHolderName" + defaultCardHolderName)
        if (defaultCardHolderName.equals(null)) {
            this._defaultCardHolderName = "CARD HOLDER NAME"
        } else {
            this._defaultCardHolderName = defaultCardHolderName
        }

    }

    /**
     * Set post url.
     *
     * @param postURL the post url
     */
    fun setPostURL(postURL: String?) {
        this.postURL = postURL
    }

    /**
     * Set payment description.
     *
     * @param paymentDescription the payment description
     */
    fun setPaymentDescription(paymentDescription: String?) {
        this.paymentDescription = paymentDescription
    }

    /**
     * Set payment metadata.
     *
     * @param paymentMetadata the payment metadata
     */
    fun setPaymentMetadata(paymentMetadata: java.util.HashMap<String, String>) {
        this.paymentMetadata = paymentMetadata
    }


    fun setSelectedAmount(selectedAmount: BigDecimal) {
        this.selectedAmount = selectedAmount
    }

    /**
     * Set payment statement descriptor.
     *
     * @param paymentDescription the payment description
     */
    fun setPaymentStatementDescriptor(paymentDescription: String?) {
        this.paymentDescription = paymentDescription
    }

    /**
     * Set initResponseModel.
     *
     * @param initResponseModel the sdkSettings
     */
    fun setInitResponse(initResponseModel: InitResponseModel?) {
        this.initResponseModel = initResponseModel
    }

    fun setBinLookupResponse(binLookupResponse: BINLookupResponse?) {
        this.binLookupResponse = binLookupResponse
    }

    /**
     * Set sdkSettings.
     *
     * @param sdkSettings the sdkSettings
     */
    fun setPaymentOptionsResponseNew(paymentOptionsResponse: PaymentOptionsResponse?) {
        this.paymentOptionsResponse = paymentOptionsResponse
    }

    fun setSelectedCurrency(selectedCurrency: String?) {
        this.selectedCurrency = selectedCurrency
    }

    /**
     * Set tapCustomer.
     *
     * @param tapCustomer the tapCustomer
     */
    fun setCustomer(tapCustomer: TapCustomer) {
        this.tapCustomer = tapCustomer
    }



    /**
     * set 3ds
     *
     * @param 3ds
     */
    fun setRequire3ds(requires3DSecure: Boolean) {
        this.requires3DSecure = requires3DSecure
    }

    /**
     * Set tokenConfig
     *
     * @param _tokenConfig the  tokenConfig
     */
    fun setTokenConfig(_tokenConfig: String?) {
        this.tokenConfig = _tokenConfig
    }

    fun setTransactionMode(transactionMode: TransactionMode?) {
        this.transactionMode = transactionMode
    }

    override fun getCurrency(): TapCurrency? {
        return currency
    }

    fun setTransactionCurrency(tapCurrency: TapCurrency) {
        currency = tapCurrency
    }

    override fun getAmount(): BigDecimal? {
        return amount
    }

    override fun getItems(): ArrayList<ItemsModel>? {
        return arrayListOf()

    }

    fun setTaxes(taxes: ArrayList<Tax>?) {
        this.taxes = taxes
    }

    override fun getTaxes(): ArrayList<Tax>? {
        return taxes
    }

    override fun getShipping(): ArrayList<Shipping>? {
        return arrayListOf()
    }

    override fun getAllowedToSaveCard(): Boolean {
        return true
    }

    override fun getAuthorizeAction(): AuthorizeAction? {
        return  null
    }


    override fun getDestination(): Destinations? {
        return Destinations()
    }

    override fun getMerchant(): Merchant? {
        return merchant
    }

    override fun getPaymentDataType(): String? {
        return paymentType
    }

    override fun getEnableEditCardHolderName(): Boolean {
        return true
    }

    override fun getTopup(): TopUp? {
        return null
    }


    override fun getSelectedAmount(): BigDecimal? {
        return BigDecimal.ONE
    }

    override fun getPaymentOptionsResponseNew(): PaymentOptionsResponse? {
        return paymentOptionsResponse
    }

    override fun getMerchantData(): MerchantData? {
        return merchantData
    }

    override fun getSDKMode(): SDKMODE {
        return sdkMode
    }

    override fun getTokenConfig(): String? {
        return tokenConfig
    }

    override fun getInitOptionsResponse(): InitResponseModel? {
        return initResponseModel
    }

    override fun getBinLookupResponse(): BINLookupResponse? {
        return binLookupResponse
    }

    override fun getCardType(): String? {
        return cardType
    }

    override fun getSelectedCurrency(): String? {
        return selectedCurrency
    }

    override fun getDefaultCardHolderName(): String? {
        return _defaultCardHolderName
    }

    override fun getTransactionMode(): TransactionMode? {
        return transactionMode
    }


    override fun getPostURL(): String? {
        return postURL
    }

    override fun getCustomer(): TapCustomer? {
        return tapCustomer
    }

    override fun getPaymentDescription(): String? {
        return paymentDescription
    }

    override fun getPaymentMetadata(): HashMap<String, String>? {
        return paymentMetadata
    }

    override fun getCardIssuer(): CardIssuer? {
        return cardIssuer
    }



    override fun getRequires3DSecure(): Boolean {
        return requires3DSecure
    }

    fun setOrder(orderObject: OrderObject) {
        // println("orderObject>>>"+orderObject.toString())
        this.orderObject = orderObject
    }

    override fun getPaymentStatementDescriptor(): String? {
        return paymentStatementDescriptor
    }


    override fun getOrderObject(): OrderObject? {
        return orderObject
    }
}

