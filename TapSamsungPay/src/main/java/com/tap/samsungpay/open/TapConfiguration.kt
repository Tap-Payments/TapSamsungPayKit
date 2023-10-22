/*
 * *
 *  * Created by $AhlaamK on 10/19/23, 11:36 AM
 *  * Copyright (c) 2023 .
 *  * Tap Payments All rights reserved.
 *  *
 *  *
 */

package com.tap.samsungpay.open

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.tap.samsungpay.internal.DataConfiguration
import com.tap.samsungpay.internal.SamsungPayActivity
import com.tap.samsungpay.internal.builder.PublicKeybuilder.Operator
import com.tap.samsungpay.internal.builder.TransactionBuilder.OrderDetail

import company.tap.tapcardformkit.open.builder.featuresBuilder.Features
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.internal.models.*
import com.tap.samsungpay.open.enums.*
import company.tap.tapcardformkit.open.builder.AuthKey


class TapConfiguration private constructor(
    val publicKey: Operator?,
    val environment: SDKMODE?,
    val scope: Scope? = Scope.TAP_TOKEN,
    val orderDetail: OrderDetail,
    val merchant: Merchant,
    val tapCustomer: TapCustomer?,
    val acceptance: Acceptance?,
  //  val fields: Fields = Fields(shipping = true, billing = true),
    val addOns: AddOns?,
    val tapInterface: TapInterface?,
   // val authToken: AuthKey?,
    val packageName: String?,
    var typeDevice: String? = "Native Android",
    val serviceId:String?,
    val orderNumber:String?
) {


    init {
        if (packageName.isNullOrBlank())
            throw IllegalArgumentException("packageName must not be null or blank.")
       // typeDevice ="Native Android"
       /* if (typeDevice.isNullOrBlank())
            throw IllegalArgumentException("device Type must not be null or blank.")*/

    }

    class Builder {
        var operator: Operator? = null
        fun setOperator(publicKey: Operator) = apply {
            this.operator = publicKey
        }

        var environment: SDKMODE? = SDKMODE.SANDBOX
        fun setEnvironment(sdkmode: SDKMODE) = apply {
            this.environment = sdkmode
        }


        var scope: Scope? =null
        fun setScope(scope: Scope) = apply {
            this.scope = scope
        }

        var orderDetail: OrderDetail? = null
        fun setOrders(orderDetail: OrderDetail) = apply {
            this.orderDetail = orderDetail
        }

        var features: Features? = null
        fun setFeatures(features: Features?) = apply {
            this.features = features
        }


        var merchant: Merchant? = null
        fun setMerchant(merchant: Merchant) = apply {
            this.merchant = merchant
        }

        var tapCustomer: TapCustomer? = null
        fun setTapCustomer(tapCustomer: TapCustomer) = apply {
            this.tapCustomer = tapCustomer
        }

        var acceptance: Acceptance? = null
        fun setAcceptance(acceptance: Acceptance) = apply {
            this.acceptance = acceptance
        }

      /*  var fields: Fields? = null
        fun setFieldsVisibility(fields: Fields?) = apply {
            this.fields = fields
        }*/

        var addOns: AddOns? = null
        fun setAddOns(addOns: AddOns?) = apply {
            this.addOns = addOns
        }

        var tapInterface: TapInterface? = null
        fun setTapInterface(tapInterface: TapInterface?) = apply {
            this.tapInterface = tapInterface
        }


       /* var authTokenn: AuthKey? = null
        fun setAuthToken(authToken: AuthKey) = apply {
            this.authTokenn = authToken
        }*/

        var packageName: String? = null
        fun setPackageName(packageName: String) = apply {
            this.packageName = packageName
        }

        var typeDevice: String? = null
        fun setDeviceType(deviceType: String) = apply {
            this.typeDevice = deviceType
        }

        var serviceId: String? = null
        fun setServiceId(serviceId: String) = apply {
            this.serviceId = serviceId
        }
        var orderNumber: String? = null
        fun setOrderNumber(orderNumber: String) = apply {
            this.orderNumber = orderNumber
        }


        fun build(): TapConfiguration {
            return TapConfiguration(
                operator,
                environment,
                scope,
                orderDetail!!,
                merchant!!,
                tapCustomer,
                acceptance,
               // fields!!,
                addOns,
                tapInterface,
               // authTokenn,
                packageName,
                typeDevice,serviceId, orderNumber
            )
        }
    }


    override fun toString(): String {
        try {
            val gson = Gson()
            return gson.toJson(this)
        } catch (e: Exception) {
            val error = Log.e("exception", e.toString())
            return error.toString()
        }

    }

    companion object {
        var tapConfigurationS: TapConfiguration? = null
        fun configureSamsungPayWithTapConfiguration(
            tapConfiguration: TapConfiguration,
            context: Context,
            tapSamsungPayDelegate: TapSamsungPayDelegate? = null

        ) {
            when (tapConfiguration.tapInterface?.theme){

                ThemeMode.DARK ->{
                  // if(context.resources.configuration.uiMode==Configuration.UI_MODE_NIGHT_YES ){
                           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                   //    }

                }
                ThemeMode.LIGHT -> {
                    if(context.resources.configuration.uiMode==Configuration.UI_MODE_NIGHT_NO ) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
                else -> {}
            }

            DataConfiguration.addSDKDelegate(tapSamsungPayDelegate)
            setTapConfiguration(tapConfiguration)
            val intent = Intent(context, SamsungPayActivity::class.java)
            context.startActivity(intent)


        }

        fun setTapConfiguration(tapConfiguration: TapConfiguration) {
            tapConfigurationS = tapConfiguration
        }

        fun getTapConfiguration(): TapConfiguration? {
            return tapConfigurationS
        }


    }


}

