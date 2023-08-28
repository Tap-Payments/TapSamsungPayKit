package com.tap.samsungpay.open

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.tap.samsungpay.internal.api.ApiService
import com.tap.samsungpay.internal.api.ItemsModel
import com.tap.samsungpay.internal.api.OrderObject
import com.tap.samsungpay.internal.interfaces.PaymentDataSourceImpl
import company.tap.tapcardformkit.internal.api.CardViewEvent
import company.tap.tapcardformkit.internal.api.SmsungPayViewModel
import company.tap.tapcardformkit.internal.api.enums.PaymentType
import company.tap.tapcardformkit.internal.api.enums.TransactionMode
import com.tap.samsungpay.internal.builder.TapConfiguration
import company.tap.tapnetworkkit.connection.NetworkApp


object DataConfiguration {
    private var sdkDelegate: SDKDelegate? = null


    fun addSDKDelegate(_sdkDelegate: SDKDelegate?) {
        println("addSDKDelegate sdk ${_sdkDelegate}")
        sdkDelegate = _sdkDelegate


    }

    fun getListener(): SDKDelegate? {
        return sdkDelegate
    }


    private fun initNetworkCallOfKit(context: Context, secretKeys: String, packageID: String) {
        NetworkApp.initNetwork(
            context,
            secretKeys,
            packageID,
            ApiService.BASE_URL,
            //   sdkIdentifier,BuildConfig.EncryptAPIKEY)
            "NATIVE",
            true,
            context.resources.getString(company.tap.tapnetworkkit_android.R.string.enryptkey),
            null
        )
    }


    fun initalizeCheckoutProfileAPi(
        context: Activity,
        dataConfig: TapConfiguration,
    ) {

        with(dataConfig) {
            PaymentDataSourceImpl.setTransactionMode(
                TransactionMode.TOKENIZE_CARD
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
            PaymentDataSourceImpl.setPaymentType(PaymentType.CARD.name)
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
        PaymentDataSourceImpl.setDefaultCardHolderName(dataConfig.tapCustomer?.nameOnCard.toString())

        NetworkApp.initNetwork(
            context, dataConfig.publicKey?.publicKey,
            dataConfig.packageName,
            ApiService.BASE_URL,
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

