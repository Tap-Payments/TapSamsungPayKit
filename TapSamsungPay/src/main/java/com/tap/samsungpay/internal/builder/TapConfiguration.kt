package com.tap.samsungpay.internal.builder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.tap.samsungpay.internal.SamsungPayActivity
import com.tap.samsungpay.internal.builder.PublicKeybuilder.Operator
import com.tap.samsungpay.internal.builder.TransactionBuilder.Transaction
import company.tap.tapcardformkit.open.builder.featuresBuilder.Features
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.internal.models.*
import com.tap.samsungpay.open.DataConfiguration
import com.tap.samsungpay.open.SDKDelegate
import com.tap.samsungpay.open.enums.*
import company.tap.tapcardformkit.open.builder.AuthKey


class TapConfiguration private constructor(
    val publicKey: Operator?,
    val environment: SDKMODE?,
    val scope: Scope? = Scope.TAPTOKEN,
    val transaction: Transaction,
    val merchant: Merchant,
    val tapCustomer: TapCustomer?,
    val acceptance: Acceptance? = Acceptance(
        supportedFundSource = SupportedFundSource.ALL,
        supportedPaymentAuthentications = SupportedPaymentAuthentications.ThreeDS
    ),
    val fields: Fields = Fields(shipping = true, billing = true),
    val addOns: AddOns?,
    val tapInterface: TapInterface?,
    val authToken: AuthKey?,
    val packageName: String?,
    val typeDevice: String?,
    val serviceId:String?
) {


    init {
        if (packageName.isNullOrBlank())
            throw IllegalArgumentException("packageName must not be null or blank.")

        if (typeDevice.isNullOrBlank())
            throw IllegalArgumentException("device Type must not be null or blank.")

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


        var scope: Scope? = null
        fun setScope(scope: Scope) = apply {
            this.scope = scope
        }

        var transaction: Transaction? = null
        fun setTransactions(transaction: Transaction) = apply {
            this.transaction = transaction
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

        var fields: Fields? = null
        fun setFields(fields: Fields?) = apply {
            this.fields = fields
        }

        var addOns: AddOns? = null
        fun setAddOns(addOns: AddOns?) = apply {
            this.addOns = addOns
        }

        var tapInterface: TapInterface? = null
        fun setTapInterface(tapInterface: TapInterface?) = apply {
            this.tapInterface = tapInterface
        }


        var authTokenn: AuthKey? = null
        fun setAuthToken(authToken: AuthKey) = apply {
            this.authTokenn = authToken
        }

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



        fun build(): TapConfiguration {
            return TapConfiguration(
                operator,
                environment,
                scope,
                transaction!!,
                merchant!!,
                tapCustomer,
                acceptance,
                fields!!,
                addOns,
                tapInterface,
                authTokenn,
                packageName,
                typeDevice,serviceId
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
            sdkDelegate: SDKDelegate? = null

        ) {
            when (tapConfiguration.tapInterface?.theme){
                ThemeMode.DARK ->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
                ThemeMode.LIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
                else -> {}
            }
            val intent = Intent(context, SamsungPayActivity::class.java)
            context.startActivity(intent)
            DataConfiguration.addSDKDelegate(sdkDelegate)
            setTapConfiguration(tapConfiguration)


        }

        fun setTapConfiguration(tapConfiguration: TapConfiguration) {
            this.tapConfigurationS = tapConfiguration
        }

        fun getTapConfiguration(): TapConfiguration? {
            return tapConfigurationS
        }


    }


}

