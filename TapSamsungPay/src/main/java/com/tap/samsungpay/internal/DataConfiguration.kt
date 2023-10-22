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
import com.tap.samsungpay.internal.models.ItemsModel
import com.tap.samsungpay.internal.models.OrderObject
import com.tap.samsungpay.internal.api.CardViewEvent
import com.tap.samsungpay.internal.api.SmsungPayViewModel
import com.tap.samsungpay.open.InternalCheckoutProfileDelegate
import com.tap.samsungpay.open.TapSamsunPayDelegate
import com.tap.samsungpay.open.TapConfiguration
import company.tap.tapcardformkit.internal.api.enums.PaymentType
import com.tap.samsungpay.internal.api.enums.TransactionMode
import com.tap.samsungpay.open.enums.SDKMODE
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapnetworkkit.connection.NetworkApp


object DataConfiguration {
    private var tapSamsunPayDelegate: TapSamsunPayDelegate? = null
    private var internalCheckoutProfileDelegate: InternalCheckoutProfileDelegate? = null


    fun addSDKDelegate(_tapSamsunPayDelegate: TapSamsunPayDelegate?) {
        println("addSDKDelegate sdk ${_tapSamsunPayDelegate}")
        tapSamsunPayDelegate = _tapSamsunPayDelegate


    }

    fun addInternalCheckoutDelegate(internalDelegate: InternalCheckoutProfileDelegate?) {
        internalCheckoutProfileDelegate = internalDelegate


    }

    fun getListener(): TapSamsunPayDelegate? {
        return tapSamsunPayDelegate
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
           /* PaymentDataSourceImpl.setSupportedPaymentMethods(this.acceptance?.supportedBrands?.map { it.rawValue }
                ?.toMutableList())*/
            PaymentDataSourceImpl.setSupportedPaymentMethods(listOf(CardBrand.SAMSUNG_PAY.name))
            PaymentDataSourceImpl.setSupportedCurrencies(
                arrayListOf(
                    this.orderDetail.currency!!
                )
            )

            PaymentDataSourceImpl.setSelectedAmount(this.orderDetail.amount?.toBigDecimal()!!)
            PaymentDataSourceImpl.setTaxes(null)
            PaymentDataSourceImpl.setDestination(null)
            PaymentDataSourceImpl.setCustomer(this.tapCustomer!!)
            PaymentDataSourceImpl.setMerchant(this.merchant)
            PaymentDataSourceImpl.setPaymentType(PaymentType.DEVICE.name)
           // PaymentDataSourceImpl.setCardType(this.acceptance?.supportedFundSource?.name)
            PaymentDataSourceImpl.setOrder(
                OrderObject(
                    amount = this.orderDetail.amount!!.toBigDecimal(),
                    currency = this.orderDetail.currency!!,
                    customer = this.tapCustomer,
                    items = arrayListOf(
                        ItemsModel(
                            amount = this.orderDetail.amount?.toBigDecimal(),
                            totalAmount = this.orderDetail.amount?.toBigDecimal(),
                            currency = this.orderDetail.currency
                        )
                    ),
                    tax = null,
                    merchant = this.merchant,
                    metaData = null
                )
            )

        }
        PaymentDataSourceImpl.setSelectedCurrency(dataConfig.orderDetail.currency)
        PaymentDataSourceImpl.setDefaultCardHolderName("")

        NetworkApp.initNetwork(
            context, dataConfig.publicKey?.publicKey,
            dataConfig.packageName,
            if (dataConfig.environment == SDKMODE.SANDBOX) ApiService.BASE_URL else ApiService.PRODUCTION_URL,
            if(dataConfig.typeDevice == null || dataConfig.typeDevice == "") "Native Android" else dataConfig.typeDevice,
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

