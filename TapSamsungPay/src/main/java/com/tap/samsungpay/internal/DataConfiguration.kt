/*
 * *
 *  * Created by $AhlaamK on 10/19/23, 11:37 AM
 *  * Copyright (c) 2023 .
 *  * Tap Payments All rights reserved.
 *  *
 *  *
 */

package com.tap.samsungpay.internal

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.tap.samsungpay.internal.api.ApiService
import com.tap.samsungpay.internal.api.ItemsModel
import com.tap.samsungpay.internal.api.OrderObject
import com.tap.samsungpay.internal.interfaces.PaymentDataSourceImpl
import com.tap.samsungpay.internal.api.CardViewEvent
import com.tap.samsungpay.internal.api.SmsungPayViewModel
import com.tap.samsungpay.open.InternalCheckoutProfileDelegate
import com.tap.samsungpay.open.SDKDelegate
import com.tap.samsungpay.open.TapConfiguration
import company.tap.tapcardformkit.internal.api.enums.PaymentType
import com.tap.samsungpay.internal.api.enums.TransactionMode
import com.tap.samsungpay.open.enums.SDKMODE
import company.tap.tapnetworkkit.connection.NetworkApp


object DataConfiguration {
    private var sdkDelegate: SDKDelegate? = null
    private var internalCheckoutProfileDelegate: InternalCheckoutProfileDelegate? = null


    fun addSDKDelegate(_sdkDelegate: SDKDelegate?) {
        println("addSDKDelegate sdk ${_sdkDelegate}")
        sdkDelegate = _sdkDelegate


    }

    fun addInternalCheckoutDelegate(internalDelegate: InternalCheckoutProfileDelegate?) {
        internalCheckoutProfileDelegate = internalDelegate


    }

    fun getListener(): SDKDelegate? {
        return sdkDelegate
    }

    fun getInternalCheckoutDelegate(): InternalCheckoutProfileDelegate? {
        return internalCheckoutProfileDelegate
    }


    fun initalizeCheckoutProfileAPi(
        context: Activity,
        dataConfig: TapConfiguration,
    ) {

        with(dataConfig) {
            PaymentDataSourceImpl.setTransactionMode(
                TransactionMode.PURCHASE
            )
            PaymentDataSourceImpl.setSupportedPaymentMethods(this.acceptance?.supportedBrands?.map { it.rawValue }
                ?.toMutableList())
            PaymentDataSourceImpl.setSupportedCurrencies(
                arrayListOf(
                    this.transaction.currency!!
                )
            )

            PaymentDataSourceImpl.setSelectedAmount(this.transaction.amount?.toBigDecimal()!!)
            PaymentDataSourceImpl.setTaxes(null)
            PaymentDataSourceImpl.setDestination(null)
            PaymentDataSourceImpl.setCustomer(this.tapCustomer!!)
            PaymentDataSourceImpl.setMerchant(this.merchant)
            PaymentDataSourceImpl.setPaymentType(PaymentType.DEVICE.name)
            PaymentDataSourceImpl.setCardType(this.acceptance?.supportedFundSource?.name)
            PaymentDataSourceImpl.setOrder(
                OrderObject(
                    amount = this.transaction.amount!!.toBigDecimal(),
                    currency = this.transaction.currency!!,
                    customer = this.tapCustomer,
                    items = arrayListOf(
                        ItemsModel(
                            amount = this.transaction.amount?.toBigDecimal(),
                            totalAmount = this.transaction.amount?.toBigDecimal(),
                            currency = this.transaction.currency
                        )
                    ),
                    tax = null,
                    merchant = this.merchant,
                    metaData = null
                )
            )

        }
        PaymentDataSourceImpl.setSelectedCurrency(dataConfig.transaction.currency)
        PaymentDataSourceImpl.setDefaultCardHolderName("")

        NetworkApp.initNetwork(
            context, dataConfig.publicKey?.publicKey,
            dataConfig.packageName,
            if (dataConfig.environment == SDKMODE.SANDBOX) ApiService.BASE_URL else ApiService.PRODUCTION_URL,
            dataConfig.typeDevice,
            true,
            context.resources.getString(company.tap.tapnetworkkit_android.R.string.enryptkey),
            null
        )



        SmsungPayViewModel().processEvent(
            CardViewEvent.InitEvent,
            null,
            context,
            activity = context as AppCompatActivity,
        )
    }

}

