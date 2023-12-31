/**
 *   Created by AhlaamK on 10/19/23, 10:56 AM
 *   Copyright (c) 2023 .
 *   Tap Payments All rights reserved.
 *
 **/

package com.tap.samsungpay.internal

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.annotation.RestrictTo
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.samsung.android.sdk.samsungpay.v2.PartnerInfo
import com.samsung.android.sdk.samsungpay.v2.SamsungPay
import com.samsung.android.sdk.samsungpay.v2.SpaySdk
import com.samsung.android.sdk.samsungpay.v2.StatusListener
import com.samsung.android.sdk.samsungpay.v2.payment.CardInfo
import com.samsung.android.sdk.samsungpay.v2.payment.CustomSheetPaymentInfo
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentInfo
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentManager
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentManager.CardInfoListener
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentManager.TransactionInfoListener
import com.samsung.android.sdk.samsungpay.v2.payment.sheet.CustomSheet
import com.tap.samsungpay.internal.api.Repository
import com.tap.samsungpay.internal.api.requests.CreateTokenSamsungPayRequest
import com.tap.samsungpay.internal.api.requests.TokenData
import com.tap.samsungpay.open.TapConfiguration
import com.tap.samsungpay.open.InternalCheckoutProfileDelegate
import com.tap.samsungpay.open.SamsungPayButton
import com.tap.samsungpay.open.enums.Scope
import com.tap.samsungpay.open.enums.ThemeMode.*
import com.tap.tapsamsungpay.R
import company.tap.tapcardvalidator_android.CardBrand
import org.json.JSONException
import org.json.JSONObject


// var SERVICE_ID = "fff80d901c2849ba8f3641"
//const val SERVICE_ID = "e5369ab0cd5141a88dd821"

@RestrictTo(RestrictTo.Scope.LIBRARY)
class SamsungPayActivity : AppCompatActivity(), InternalCheckoutProfileDelegate {
    private lateinit var partnerInfo: PartnerInfo
    private lateinit var paymentManager: PaymentManager
    private lateinit var samsungPayButton: SamsungPayButton
    private lateinit var SERVICE_ID:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.samsung_pay_acitivity)
        samsungPayButton = findViewById(R.id.samsung_view)

        DataConfiguration.addInternalCheckoutDelegate(this)


        val bundle = Bundle()
        bundle.putString(SpaySdk.PARTNER_SERVICE_TYPE, SpaySdk.ServiceType.INAPP_PAYMENT.toString())
        /**
         * we need service ID for creating the partner info and service type
         */
        SERVICE_ID = TapConfiguration.getTapConfiguration()?.serviceId ?:""
       // Log.e("serviceID",SERVICE_ID.toString())
        partnerInfo = PartnerInfo(SERVICE_ID, bundle)
        updateSamsungPayButton()
        samsungPayButton.buttonSamsung.setOnClickListener {
              startInAppPayWithCustomSheet()
        }

    }


    private fun updateSamsungPayButton() {
        val samsungPay = SamsungPay(this, partnerInfo)

        samsungPay.getSamsungPayStatus(object : StatusListener {
            override fun onSuccess(status: Int, bundle: Bundle) {
                when (status) {
                    SpaySdk.SPAY_READY -> {
                        DataConfiguration.getListener()?.onReady("SPAY_READY")

                        /**
                         * start Checkout transaction
                         */
                         startCallForCheckoutProfileAPi()
                        // Perform your operation. of inApp Payment
                        /**
                         * start In App Pay for for normal Transaction
                         */
                      //  startInAppPayment()


                    }
                    SpaySdk.SPAY_NOT_READY -> {

                        DataConfiguration.getListener()?.onError("SPAY_NOT_READY")

                        // Samsung Pay is supported but not fully ready.

                        // If EXTRA_ERROR_REASON is ERROR_SPAY_APP_NEED_TO_UPDATE,
                        // Call goToUpdatePage().

                        // If EXTRA_ERROR_REASON is ERROR_SPAY_SETUP_NOT_COMPLETED,
                        // Call activateSamsungPay().
                        val extraError = bundle.getInt(SamsungPay.EXTRA_ERROR_REASON)
                        if (extraError == SamsungPay.ERROR_SPAY_SETUP_NOT_COMPLETED) {
                            doActivateSamsungPay(SpaySdk.ServiceType.INAPP_PAYMENT.toString())
                        }
                    }
                    SpaySdk.SPAY_NOT_ALLOWED_TEMPORALLY -> {
                        DataConfiguration.getListener()?.onError("SPAY_NOT_ALLOWED_TEMPORALLY")


                        // If EXTRA_ERROR_REASON is ERROR_SPAY_CONNECTED_WITH_EXTERNAL_DISPLAY,
                        // guide user to disconnect it.
                    }
                    SpaySdk.SPAY_NOT_SUPPORTED -> {
                      //  startCallForCheckoutProfileAPi()

                        DataConfiguration.getListener()?.onError("SPAY_NOT_SUPPORTED")
                    }

                }
            }

            override fun onFail(errorCode: Int, bundle: Bundle?) {
                // If EXTRA_ERROR_REASON is ERROR_SPAY_SETUP_NOT_COMPLETED,
// Call activateSamsungPay().

//
//                Toast.makeText(
//                    this@SamsungPayActivity,
//                    "Samsung Pay on Failure",
//                    Toast.LENGTH_SHORT
//                ).show()

                DataConfiguration.getListener()?.onError(errorCode.toString())
            }
        })
    }

    private fun startCallForCheckoutProfileAPi() {
       // println("TapConfiguration.getTapConfiguration()"+ TapConfiguration.getTapConfiguration())
        with(TapConfiguration.getTapConfiguration()) {
            this?.let {
                DataConfiguration.initalizeCheckoutProfileAPi(
                    context = this@SamsungPayActivity,
                    it
                )
            }
        }


    }

    override fun onResume() {
        super.onResume()
        samsungPayButton.startShimmer()

    }

    override fun onPause() {
        super.onPause()
        samsungPayButton.stopShimmer()

    }

    private fun doActivateSamsungPay(serviceType: String) {
        val bundle = Bundle()
        bundle.putString(SamsungPay.PARTNER_SERVICE_TYPE, serviceType)
        val partnerInfo = PartnerInfo(SERVICE_ID, bundle)
        val samsungPay = SamsungPay(this, partnerInfo)
        samsungPay.activateSamsungPay()
    }

    /*
 * PaymentManager.startInAppPayWithCustomSheet is a method to request online(in-app) payment with Samsung Pay.
 * Partner app can use this method to make in-app purchase using Samsung Pay from their
 * application with custom payment sheet.
 */
    fun startInAppPayWithCustomSheet() {

        val bundle = Bundle()
        bundle.putString(
            SamsungPay.PARTNER_SERVICE_TYPE,
            SpaySdk.ServiceType.INAPP_PAYMENT.toString()
        )

        val partnerInfo = PartnerInfo(SERVICE_ID, bundle)
        paymentManager = PaymentManager(this, partnerInfo)
        paymentManager.requestCardInfo(Bundle(), cardInfoListener)

        /*
         * PaymentManager.startInAppPay is a method to request online (In-App) payment
         * with Samsung Pay.
         * Merchant app can use this method to make In-App purchase using Samsung Pay from their
         * application with normal payment sheet.
         */


        val samsungPayTransaction = SamsungPayTransaction()
        paymentManager = PaymentManager(
            this,
            partnerInfo
        )

        paymentManager.startInAppPayWithCustomSheet(
            samsungPayTransaction.makeTransactionDetailsWithSheet(),
            transactionInfoListener
        )


    }

    //Not used for now
    private fun startInAppPayment() {

        try {


            val bundle = Bundle()
            bundle.putString(
                SamsungPay.PARTNER_SERVICE_TYPE,
                SpaySdk.ServiceType.INAPP_PAYMENT.toString()
            )

            val partnerInfo = PartnerInfo(SERVICE_ID, bundle)
            paymentManager = PaymentManager(this, partnerInfo)
            paymentManager.requestCardInfo(Bundle(), cardInfoListener)

            paymentManager.startInAppPay(
                makeTransactionDetails(),
                object : TransactionInfoListener {
                    override fun onAddressUpdated(p0: PaymentInfo?) {
                        Toast.makeText(
                            this@SamsungPayActivity,
                            "address Updated",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onCardInfoUpdated(p0: CardInfo?) {
                        Toast.makeText(this@SamsungPayActivity, "card Updated", Toast.LENGTH_SHORT)
                            .show()

                    }

                    override fun onSuccess(
                        p0: PaymentInfo?,
                        paymentCredential: String?,
                        bundle: Bundle?
                    ) {
                        Toast.makeText(
                            this@SamsungPayActivity,
                            "success  $paymentCredential",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("error", bundle.toString())

                    }

                    override fun onFailure(errorCode: Int, bundle: Bundle?) {
                        Toast.makeText(
                            this@SamsungPayActivity,
                            "failure error code ${errorCode} , bundle data : ${bundle.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("error", bundle.toString())

                    }

                })
        } catch (e: Exception) {
            Log.e("exception thrown", e.toString())
            Toast.makeText(
                this@SamsungPayActivity,
                "exception thrown ${e.message.toString()}",
                Toast.LENGTH_LONG
            ).show();

        }
    }

    private fun makeTransactionDetails(): PaymentInfo? {
        val brandList: ArrayList<SpaySdk.Brand> = ArrayList()
        // If the supported brand is not specified, all card brands in Samsung Pay are listed
        // in the Payment Sheet.
        brandList.add(SpaySdk.Brand.MASTERCARD)
        brandList.add(SpaySdk.Brand.VISA)
        brandList.add(SpaySdk.Brand.AMERICANEXPRESS)
        val shippingAddress =
            PaymentInfo.Address.Builder()
                .setAddressee("name")
                .setAddressLine1("addLine1")
                .setAddressLine2("addLine2")
                .setCity("city")
                .setState("state")
                .setCountryCode("United States")
                .setPostalCode("zip")
                .build()
        val amount =
            PaymentInfo.Amount.Builder()
                .setCurrencyCode("USD")
                .setItemTotalPrice("1000")
                .setShippingPrice("10")
                .setTax("50")
                .setTotalPrice("1060")
                .build()
        return PaymentInfo.Builder()
            .setMerchantId("123456")
            .setMerchantName("Sample Merchant")
            .setOrderNumber("AMZ007MAR")
            .setPaymentProtocol(PaymentInfo.PaymentProtocol.PROTOCOL_3DS) // Merchant requires billing address from Samsung Pay and
            // sends the shipping address to Samsung Pay.
            // Option shows both billing and shipping address on the payment sheet.
            .setAddressInPaymentSheet(PaymentInfo.AddressInPaymentSheet.NEED_BILLING_SEND_SHIPPING)
            .setShippingAddress(shippingAddress)
            .setAllowedCardBrands(brandList)
            .setCardHolderNameEnabled(true)
            .setRecurringEnabled(false)
            .setAmount(amount)
            .build()
    }

    private val cardInfoListener: CardInfoListener = object : CardInfoListener {
        /*
     * This callback is received when the card information is received successfully.
     */
        override fun onResult(cardResponse: List<CardInfo>) {
            var visaCount = 0
            var mcCount = 0
            var amexCount = 0
            var dsCount = 0
             var brandStrings = "- Card Info : "
            if (cardResponse != null) {
                 var brand: SpaySdk.Brand?
                for (i in cardResponse.indices) {
                     brand = cardResponse[i].brand
                    when (brand) {
                        SpaySdk.Brand.AMERICANEXPRESS -> amexCount++
                        SpaySdk.Brand.MASTERCARD -> mcCount++
                        SpaySdk.Brand.VISA -> visaCount++
                        SpaySdk.Brand.DISCOVER -> dsCount++
                        else -> {}
                    }
                }
            }
            brandStrings += "  VI=$visaCount, MC=$mcCount, AX=$amexCount, DS=$dsCount"

        }

        /*
     * This callback is received when the card information cannot be retrieved.
     * For example, when SDK service in the Samsung Pay app dies abnormally.
     */
        override fun onFailure(errorCode: Int, errorData: Bundle) {
            //Called when an error occurs during In-App cryptogram generation
            DataConfiguration.getListener()?.onError("Failed to retrieved"+errorData.toString())
        }
    }


    /*
     * CustomSheetTransactionInfoListener is for listening callback events of online (in-app) custom sheet payment.
     * This is invoked when card is changed by the user on the custom payment sheet,
     * and also with the success or failure of online (in-app) payment.
     */
    private val transactionInfoListener: PaymentManager.CustomSheetTransactionInfoListener =
        object : PaymentManager.CustomSheetTransactionInfoListener {
            // This callback is received when the user changes card on the custom payment sheet in Samsung Pay.
            override fun onCardInfoUpdated(selectedCardInfo: CardInfo, customSheet: CustomSheet) {
                /*
                 * Called when the user changes card in Samsung Pay.
                 * Newly selected cardInfo is passed and partner app can update transaction amount based on new card (if needed).
                 * Call updateSheet() method. This is mandatory.
                 */
                paymentManager.updateSheet(customSheet)
            }

            override fun onSuccess(
                response: CustomSheetPaymentInfo,
                paymentCredential: String,
                extraPaymentData: Bundle
            ) {
                /*
                 * You will receive the payloads shown below in paymentCredential parameter
                 * The output paymentCredential structure varies depending on the PG you're using and the integration model (direct, indirect) with Samsung.
                 */
                println("on success response>"+TapConfiguration.getTapConfiguration()?.scope)
              //  println("on success paymentCredential>"+paymentCredential)
              ///  println("on success TapConfiguration.getTapConfiguration()?.scope>"+ TapConfiguration.getTapConfiguration()?.scope)

               // println("on success extraPaymentData>"+extraPaymentData)
                if(TapConfiguration.getTapConfiguration()?.scope==Scope.SAMSUNG_TOKEN) {
                    DataConfiguration.getListener()?.onSamsungPayToken(paymentCredential)
                    this@SamsungPayActivity.finish()
                }else if( TapConfiguration.getTapConfiguration()?.scope==Scope.TAP_TOKEN ){

                    /*
                     *You will now be  passing samsung token to tap api
                     * **/
                    handleSuccessCallBack(paymentCredential)
                }


            }

            // This callback is received when the online payment transaction has failed.
            override fun onFailure(errorCode: Int, errorData: Bundle?) {
               // println("errorData>>>" + errorData)
                Log.e("onFailure >>>>>", errorCode.toString() )
                if(errorCode.toString()!=null && errorCode==-7){
                    DataConfiguration.getListener()?.onCancel("Payment cancelled")
                    finish()

                }else DataConfiguration.getListener()?.onError("Failed to show"+errorData.toString())

            }
        }

    override fun onError(error: String?) {
        Log.e("SPAYY >>>>>", error.toString() )
        //samsungPayButton.visibility = View.GONE

    }

    override fun onSuccess() {
        samsungPayButton.stopShimmer();
        val samsungPayPaymentOption =
            PaymentDataSourceImpl.paymentOptionsResponse?.paymentOptions?.firstOrNull { it.brand == CardBrand.SAMSUNG_PAY }
        samsungPayButton.applyStyleToSamsungButton(samsungPayPaymentOption)

    }

    private fun handleSuccessCallBack(paymentData: String) {
        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        try {
            val _paymentData = JSONObject(paymentData).getJSONObject("3DS")
            println("paymentData$_paymentData")

            val token = _paymentData.getString("data")

                val gson = Gson()
                val jsonToken = gson.fromJson(token, JsonElement::class.java)

           // println("jsonToken"+jsonToken)
                /**
                 * At this stage, Passing the samsungPaylaod to Tap Backend TokenAPI call followed by chargeAPI.
                 */
                val createTokenSamsungPayRequest =
                    CreateTokenSamsungPayRequest(
                        TokenData(jsonToken),"samsungpay"
                    )
          Repository().getSPayTokenRequest(this,createTokenSamsungPayRequest)

        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}