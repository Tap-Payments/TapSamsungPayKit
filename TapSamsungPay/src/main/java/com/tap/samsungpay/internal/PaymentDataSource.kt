package com.tap.samsungpay.internal

import com.tap.samsungpay.internal.api.*
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import company.tap.checkout.open.models.Destinations
import company.tap.tapcardformkit.internal.api.enums.TransactionMode
import company.tap.tapcardformkit.internal.api.models.*
import company.tap.tapcardformkit.internal.api.responses.InitResponseModel
import company.tap.tapcardformkit.internal.api.responses.MerchantData
import com.tap.samsungpay.internal.api.responses.PaymentOptionsResponse
import com.tap.samsungpay.internal.models.TapCustomer
import com.tap.samsungpay.open.enums.SDKMODE
import java.math.BigDecimal

/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
interface PaymentDataSource {


    /**
     * Transaction currency. @return the currency
     */
    fun getCurrency(): TapCurrency?



    /**
     * Amount. Either amount or items should return nonnull value. If both return nonnull, then items is preferred. @return the amount
     */
    fun getAmount(): BigDecimal?

    /**
     * List of items to pay for. Either amount or items should return nonnull value. If both return nonnull, then items is preferred. @return the items
     */
    fun getItems(): ArrayList<ItemsModel>?



    /**
     * List of taxes. Optional. Note: specifying taxes will affect total payment amount. @return the taxes
     */
    fun getTaxes(): ArrayList<Tax>?

    /**
     * Shipping list. Optional. Note: specifying shipping will affect total payment amount. @return the shipping
     */
    fun getShipping(): ArrayList<Shipping>?




    /**
     * Defines if user allowed to save card. @return the allowUserToSaveCard
     * @return
     */
    fun getAllowedToSaveCard(): Boolean


    /**
     * Action to perform after authorization succeeds. Used only if transaction mode is AUTHORIZE_CAPTURE. @return the authorize action
     */
    fun getAuthorizeAction(): AuthorizeAction?

    /**
     * The Destination array contains list of Merchant desired destinations accounts to receive money from payment transactions
     */
    fun getDestination(): Destinations?

    fun getMerchant(): Merchant?
    fun getPaymentDataType(): String?



    /**
     * Defines if user allowed to edit the cardHolderName. @return the enableEditCardHolderName
     * @return
     */
    fun getEnableEditCardHolderName(): Boolean



    fun getTopup(): TopUp?


    fun getSelectedAmount(): BigDecimal?

    fun getPaymentOptionsResponseNew(): PaymentOptionsResponse?


    fun getMerchantData(): MerchantData?

    /**
     * Defines the SDK mode . Optional. @return the default Sandbox
     */
    fun getSDKMode(): SDKMODE?

    /**
     * Defines the TokenConfig for header
     */
    fun getTokenConfig(): String?

    fun getInitOptionsResponse(): InitResponseModel?

    fun getBinLookupResponse(): BINLookupResponse?
    /**
     * Defines if user wants all cards or specific card types.
     */
    fun getCardType(): String?

    fun getSelectedCurrency(): String?
    fun getDefaultCardHolderName(): String?

    fun getTransactionMode() : TransactionMode?
    /**
     * Tap will post to this URL after transaction finishes. Optional. @return the post url
     */
    fun getPostURL(): String?

    /**
     * TapCustomer. @return the customer
     */
    fun getCustomer(): TapCustomer?
    /**
     * Description of the payment. @return the payment description
     */
    fun getPaymentDescription(): String?

    /**
     * If you would like to pass additional information with the payment, pass it here. @return the payment metadata
     */
    fun getPaymentMetadata(): HashMap<String, String>?
    /**
     * Defines the cardIssuer details. Optional. @return the default CardIssuer
     */
    fun getCardIssuer(): CardIssuer?



    /**
     * Defines if 3D secure check is required. @return the requires 3 d secure
     */
    fun getRequires3DSecure(): Boolean

    /**
     * Payment statement descriptor. @return the payment statement descriptor
     */
    fun getPaymentStatementDescriptor(): String?


    fun getOrderObject(): OrderObject?




}

