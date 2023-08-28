package com.tap.samsungpay.internal.api

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.internal.api.responses.PaymentOptionsResponse
import com.tap.samsungpay.internal.interfaces.PaymentDataSourceImpl
import company.tap.checkout.open.models.Destinations
import company.tap.tapcardformkit.internal.api.SmsungPayViewModel
import company.tap.tapcardformkit.internal.api.CardViewState
import company.tap.tapcardformkit.internal.api.enums.TransactionMode
import company.tap.tapcardformkit.internal.api.models.*
import company.tap.tapcardformkit.internal.api.responses.*
import company.tap.tapcardformkit.open.*
import company.tap.tapnetworkkit.connection.NetworkApp
import company.tap.tapnetworkkit.controller.NetworkController
import company.tap.tapnetworkkit.enums.TapMethodType
import company.tap.tapnetworkkit.exception.GoSellError
import company.tap.tapnetworkkit.interfaces.APIRequestCallback
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Response
import java.math.BigDecimal
import java.util.*


/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
class Repository : APIRequestCallback {
    val resultObservable = BehaviorSubject.create<CardViewState>()
    lateinit var tokenResponse: Token
    lateinit var cardRepositoryContext: Context
    private lateinit var smsungPayViewModel: SmsungPayViewModel
    private var activity: AppCompatActivity? = null
    var merchantDataModel: MerchantData? = null
    var assetsModel: AssetsModel? = null

    @JvmField
    var initResponseModel: InitResponseModel? = null

    @JvmField
    var paymentOptionsResponse: PaymentOptionsResponse? = null

    fun getInitData(
        _context: Context,
        smsungPayViewModel: SmsungPayViewModel?
    ) {
        if (smsungPayViewModel != null) {
            this.smsungPayViewModel = smsungPayViewModel
        }


        val requestBody = PaymentOptionsRequest(
            PaymentDataSourceImpl.getTransactionMode(),
            PaymentDataSourceImpl.getAmount(),
            PaymentDataSourceImpl.getShipping(),
            PaymentDataSourceImpl.getTaxes(),
            PaymentDataSourceImpl.getDestination(),
            PaymentDataSourceImpl.getCurrency()?.isoCode,
            PaymentDataSourceImpl.getCustomer()?.identifier,
            PaymentDataSourceImpl.getMerchant()?.id,
            PaymentDataSourceImpl.getPaymentDataType().toString(),
            PaymentDataSourceImpl.getTopup(),
            PaymentDataSourceImpl.getOrderObject(),
            PaymentDataSourceImpl.getSupportedPaymentMethods(),
            PaymentDataSourceImpl.getCardType(),
            PaymentDataSourceImpl.getSupportedCurrencies()
        )

        val jsonString = Gson().toJson(requestBody)
        println("requestBody$jsonString")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkController.getInstance().processRequest(
                TapMethodType.POST,
                ApiService.INIT,
                jsonString,
                this,
                INIT_CODE
            )
        }
        this.cardRepositoryContext = _context

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun createTokenWithEncryptedCard(

        createTokenWithCardDataRequest: CreateTokenCard?, activity: AppCompatActivity?
    ) {
        this.activity = activity
        val createTokenWithCardDataReq = createTokenWithCardDataRequest?.let {

        }
//        val jsonString = Gson().toJson(createTokenWithCardDataReq)
//        Log.e("crypteddataToBeSend", jsonString)
//        NetworkController.getInstance().processRequest(
//            TapMethodType.POST, ApiService.TOKEN, jsonString,
//            this, CREATE_TOKEN_CODE
//        )
//        if (tapCardInputView != null) {
//            this._tapCardInputView = tapCardInputView
//        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onSuccess(responseCode: Int, requestCode: Int, response: Response<JsonElement>?) {
        if (requestCode == INIT_CODE) {
            if (response?.body() != null) {
                response.body().let {

                    if (response.body() != null) {
                        Log.e("response",response.body().toString())

                        response.body().let {
                            initResponseModel = Gson().fromJson(it, InitResponseModel::class.java)
                            paymentOptionsResponse = initResponseModel?.paymentOptionsResponse
                            merchantDataModel = initResponseModel?.merchant
                            assetsModel = initResponseModel?.assestsModel
                            //   logsModel = initResponseModel?.merchant?.logs?.name

//                            if (LocalizationManager.currentLocalized.length() == 0) {
//                                LocalizationManager.setLocale(cardRepositoryContext, Locale("en"))
//                                LocalizationManager.loadTapLocale(
//                                    cardRepositoryContext,
//                                    assetsModel?.localisation?.cardUrlLocale?.urlLocale.toString()
//                                )
//                                Log.e(
//                                    "local",
//                                    assetsModel?.localisation?.cardUrlLocale?.urlLocale.toString()
//                                )
//
//                            }
//
//                            //  if (ThemeManager.currentTheme.isNullOrEmpty()){
//                            when (cardRepositoryContext.resources?.configuration?.uiMode?.and(
//                                Configuration.UI_MODE_NIGHT_MASK
//                            )) {
//                                Configuration.UI_MODE_NIGHT_YES -> {
//
//                                    ThemeManager.currentThemeName = ThemeMode.dark.name
//
//                                    ThemeManager.loadTapTheme(
//                                        cardRepositoryContext,
//                                        assetsModel?.theme?.cardThemeObj?.darkUrl.toString(),
//                                        ThemeManager.currentThemeName
//                                    )
//                                }
//                                Configuration.UI_MODE_NIGHT_NO -> {
//                                    ThemeManager.currentThemeName = ThemeMode.light.name
//
//                                    ThemeManager.loadTapTheme(
//                                        cardRepositoryContext,
//                                        assetsModel?.theme?.cardThemeObj?.lightUrl.toString(),
//                                        ThemeManager.currentThemeName
//                                    )
//
//                                }
//                                Configuration.UI_MODE_NIGHT_UNDEFINED -> {
//
//                                }
//                                else -> {
//                                    ThemeManager.currentThemeName = ThemeMode.light.name
//                                    ThemeManager.loadTapTheme(
//                                        cardRepositoryContext,
//                                        assetsModel?.theme?.cardThemeObj?.lightUrl.toString(),
//                                        ThemeManager.currentThemeName
//                                    )
//                                }
//                            }
//                            //    }

                            NetworkApp.initNetworkToken(
                                initResponseModel?.session,
                                cardRepositoryContext,
                                ApiService.BASE_URL,
                                true,
                                null
                            )
                            PaymentDataSourceImpl.setPaymentOptionsResponseNew(
                                paymentOptionsResponse
                            )
                            PaymentDataSourceImpl.setMerchantData(merchantDataModel)
                            PaymentDataSourceImpl.setInitResponse(initResponseModel)
                            PaymentDataSourceImpl.setTokenConfig(initResponseModel?.session)

                        }

                    } else {

                    }

                }

            } else {


            }

        } else if (requestCode == CREATE_TOKEN_CODE) {
//            response?.body().let {
//                tokenResponse = Gson().fromJson(it, Token::class.java)
//                if (PaymentDataSource.getTransactionMode() == TransactionMode.SAVE_CARD) {
//                    createSaveCard(null, tokenResponse.id)
//                } else {
//                    tokenizeParams.getListener()?.cardTokenizedSuccessfully(tokenResponse)
//                    _tapCardInputView.showSuccess()
//
//                }
//            }


        }
    }


    override fun onFailure(requestCode: Int, errorDetails: GoSellError?) {
        Log.e("response",errorDetails?.errorMessage.toString())

        errorDetails?.let {
            if (it.errorBody != null) {
//                resultObservable.onError(it.throwable)
              //  tokenizeParams.tapCardFormConfigurationDelegate?.onError(it.errorBody)


                //   tokenizeParams.getListener()?.backendUnknownError("Required fields are empty")
              //  _tapCardInputView.showFailure()

            } else
                try {
                    // resultObservable.onError(Throwable(it.errorMessage))
                    RxJavaPlugins.setErrorHandler(Throwable::printStackTrace)
                    if (requestCode == CREATE_TOKEN_CODE) {
                    //    tokenizeParams.getListener()?.cardTokenizedFailed(errorDetails)

                    } else {

                    }


                } catch (e: Exception) {

                }


        }
    }


    companion object {
        private const val INIT_CODE = 2
        private const val CREATE_TOKEN_CODE = 3
    }


}

class PaymentOptionsRequest(
    transactionMode: TransactionMode?,
    amount: BigDecimal?,
    shipping: java.util.ArrayList<Shipping>?,
    taxes: java.util.ArrayList<Tax>?,
    destinations: Destinations?,
    currency: String?,
    customer: String?,
    merchant_id: String?,
    payment_type: String,
    topup: TopUp?,
    orderObject: OrderObject?,
    supportedPaymentMethods: List<String>? = null,
    cardType: String? = null,
    supportedCurrencies: List<String>? = null,


    ) {
    @SerializedName("transaction_mode")
    @Expose
    private val transactionMode: TransactionMode = transactionMode ?: TransactionMode.TOKENIZE_CARD

    @SerializedName("items")
    @Expose
    private var items: java.util.ArrayList<PaymentItem>? = null

    @SerializedName("shipping")
    @Expose
    private val shipping: java.util.ArrayList<Shipping>? = shipping

    @SerializedName("taxes")
    @Expose
    private val taxes: java.util.ArrayList<Tax>? = taxes


    @SerializedName("destinations")
    @Expose
    private val destinations: Destinations? = destinations

    @SerializedName("customer")
    @Expose
    private val customer: String? = customer

    @SerializedName("currency")
    @Expose
    private val currency: String? = currency

    @SerializedName("total_amount")
    @Expose
    private var totalAmount: BigDecimal? = amount

    @SerializedName("merchant_id")
    @Expose
    @Nullable
    private val merchant_id: String? = merchant_id

    @SerializedName("payment_type")
    @Expose
    private val payment_type: String = payment_type

    @SerializedName("card_type")
    @Expose
    private val card_type: String? = cardType

    @SerializedName("supported_payment_methods")
    @Expose
    private val supported_payment_methods: List<String>? = supportedPaymentMethods

    @SerializedName("supported_currencies")
    @Expose
    private val supported_currencies: List<String>? = supportedCurrencies

    @SerializedName("topup")
    @Expose
    private val topup: TopUp? = topup

    @SerializedName("order")
    @Expose
    private val orderObject: OrderObject? = orderObject
}

data class PaymentItem(
    var name: String,
    var description: String,
    var quantity: Quantity,
    var amountPerUnit: BigDecimal,
    @Nullable var discount: AmountModificator?,
    @Nullable var taxes: java.util.ArrayList<Tax>?, var totalAmount: BigDecimal? = null
)

class Quantity(

)

