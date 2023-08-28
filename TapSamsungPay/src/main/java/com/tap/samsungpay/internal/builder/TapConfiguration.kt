package company.tap.tapcardformkit.open.builder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.tap.samsungpay.internal.SamsungPayActivity
import com.tap.samsungpay.open.enums.Scope
import company.tap.tapcardformkit.open.builder.PublicKeybuilder.PublicKeyConfiguration
import com.tap.samsungpay.internal.builder.TransactionBuilder.Transaction
import company.tap.tapcardformkit.open.builder.featuresBuilder.Features
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.open.DataConfiguration
import com.tap.samsungpay.open.SDKDelegate
import com.tap.samsungpay.open.enums.SDKMODE
import company.tap.tapcardformkit.open.models.*


class TapConfiguration private constructor(
    val publicKey: PublicKeyConfiguration?,
    val environment: SDKMODE?,
    val scope: Scope,
    val transaction: Transaction,
    val merchant: Merchant,
    val tapCustomer: TapCustomer?,
    val acceptance: Acceptance?,
    val fields: Fields?,
    val addOns: AddOns?,
    val tapInterface: TapInterface?,
    val authToken: AuthKey?,
    val packageName: String?,
    val typeDevice: String?,
) {


    init {
        if (packageName.isNullOrBlank())
            throw IllegalArgumentException("packageName must not be null or blank.")

        if (typeDevice.isNullOrBlank())
            throw IllegalArgumentException("device Type must not be null or blank.")

    }

    class Builder {
        var publicKey: PublicKeyConfiguration? = null
        fun setPublicKey(publicKey: PublicKeyConfiguration) = apply {
            this.publicKey = publicKey
        }

        var environment: SDKMODE? = SDKMODE.SANDBOX
        fun setEnvironment(sdkmode: SDKMODE) = apply {
            this.environment = sdkmode
        }


        var scope: Scope = Scope.TAPTOKEN
        fun setScope(scope: Scope) = apply {
            this.scope = scope
        }

        var transaction: Transaction? = null
        fun setTransactions(transaction: Transaction) = apply {
            this.transaction = transaction
        }


//        var order: Order? = null
//        fun setOrder(order: Order) = apply {
//            this.order = order
//        }


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


        fun build(): TapConfiguration {
            return TapConfiguration(
                publicKey,
                environment,
                scope,
                transaction!!,
                merchant!!,
                tapCustomer,
                acceptance,
                fields,
                addOns,
                tapInterface,
                authTokenn,
                packageName,
                typeDevice
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
        fun configureSamsungPayWithTapConfiguration(
            tapConfiguration: TapConfiguration,
            context: Context,
            sdkDelegate: SDKDelegate? = null

        ) {
            val intent = Intent(context, SamsungPayActivity::class.java)
            context.startActivity(intent)

            with(tapConfiguration) {

                DataConfiguration.initalizeCheckoutProfileAPi(
                    context = context as Activity,
                    tapConfiguration
                )
                DataConfiguration.addSDKDelegate(sdkDelegate)
            }

        }


    }


}

