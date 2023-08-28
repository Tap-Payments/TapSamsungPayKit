package com.tap.samsungpay.internal

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.annotation.RestrictTo
import com.samsung.android.sdk.samsungpay.v2.PartnerInfo
import com.samsung.android.sdk.samsungpay.v2.SamsungPay
import com.samsung.android.sdk.samsungpay.v2.SpaySdk
import com.samsung.android.sdk.samsungpay.v2.StatusListener
import com.samsung.android.sdk.samsungpay.v2.payment.CardInfo
import com.samsung.android.sdk.samsungpay.v2.payment.CustomSheetPaymentInfo
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentManager
import com.samsung.android.sdk.samsungpay.v2.payment.sheet.CustomSheet
import com.tap.samsungpay.open.DataConfiguration
import com.tap.samsungpay.open.SDKDelegate

@RestrictTo(RestrictTo.Scope.LIBRARY)
class SamsungPayActivity : Activity() {
    private lateinit var partnerInfo: PartnerInfo
    private lateinit var paymentManager: PaymentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = Bundle()
        bundle.putString(SpaySdk.PARTNER_SERVICE_TYPE, SpaySdk.ServiceType.INAPP_PAYMENT.toString())
        /**
         * we need service ID for creating the partner info and service type
         */
        partnerInfo = PartnerInfo("SERVICE_ID", bundle)
        updateSamsungPayButton()

    }

    private fun updateSamsungPayButton() {
        val samsungPay = SamsungPay(this, partnerInfo)

        samsungPay.getSamsungPayStatus(object : StatusListener {
            override fun onSuccess(status: Int, bundle: Bundle) {
                when (status) {
                    SpaySdk.SPAY_READY -> {
                        DataConfiguration.getListener()?.onError("SPAY_READY")
                        Toast.makeText(
                            this@SamsungPayActivity,
                            "Samsung Pay Ready",
                            Toast.LENGTH_SHORT
                        ).show()
//                        dataBinding.samsungPayButton.visibility = View.VISIBLE

                        // Perform your operation.
                    }
                    SpaySdk.SPAY_NOT_READY -> {

                        DataConfiguration.getListener()?.onError("SPAY_NOT_READY")

                        Toast.makeText(
                            this@SamsungPayActivity,
                            "Samsung Pay is supported but not fully ready",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Samsung Pay is supported but not fully ready.

                        // If EXTRA_ERROR_REASON is ERROR_SPAY_APP_NEED_TO_UPDATE,
                        // Call goToUpdatePage().

                        // If EXTRA_ERROR_REASON is ERROR_SPAY_SETUP_NOT_COMPLETED,
                        // Call activateSamsungPay().
                        val extraError = bundle.getInt(SamsungPay.EXTRA_ERROR_REASON)
                        if (extraError == SamsungPay.ERROR_SPAY_SETUP_NOT_COMPLETED) {
                            doActivateSamsungPay(SpaySdk.ServiceType.INAPP_PAYMENT.toString())
                        }

                        //dataBinding.samsungPayButton.visibility = View.INVISIBLE
                    }
                    SpaySdk.SPAY_NOT_ALLOWED_TEMPORALLY -> {
                        DataConfiguration.getListener()?.onError("SPAY_NOT_ALLOWED_TEMPORALLY")

                        Toast.makeText(
                            this@SamsungPayActivity,
                            "Samsung Pay SPAY_NOT_ALLOWED_TEMPORALLY",
                            Toast.LENGTH_SHORT
                        ).show()
                        // If EXTRA_ERROR_REASON is ERROR_SPAY_CONNECTED_WITH_EXTERNAL_DISPLAY,
                        // guide user to disconnect it.

                        //  dataBinding.samsungPayButton.visibility = View.INVISIBLE
                    }
                    SpaySdk.SPAY_NOT_SUPPORTED -> {
                        DataConfiguration.getListener()?.onError("SPAY_NOT_SUPPORTED")
//                        Toast.makeText(
//                            this@SamsungPayActivity,
//                            "Samsung Pay SPAY_NOT_SUPPORTED",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //     dataBinding.samsungPayButton.visibility = View.INVISIBLE
                    }

                }
            }

            override fun onFail(errorCode: Int, bundle: Bundle?) {
                // If EXTRA_ERROR_REASON is ERROR_SPAY_SETUP_NOT_COMPLETED,
// Call activateSamsungPay().


                Toast.makeText(
                    this@SamsungPayActivity,
                    "Samsung Pay on Failure",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun doActivateSamsungPay(serviceType: String) {
        val bundle = Bundle()
        bundle.putString(SamsungPay.PARTNER_SERVICE_TYPE, serviceType)
        val partnerInfo = PartnerInfo("SERVICE_ID", bundle)
        val samsungPay = SamsungPay(this, partnerInfo)
        samsungPay.activateSamsungPay()
    }

    /*
 * PaymentManager.startInAppPayWithCustomSheet is a method to request online(in-app) payment with Samsung Pay.
 * Partner app can use this method to make in-app purchase using Samsung Pay from their
 * application with custom payment sheet.
 */
    fun startInAppPayWithCustomSheet() {
        val samsungPayTransaction = SamsungPayTransaction()
        paymentManager = PaymentManager(applicationContext, partnerInfo)

        paymentManager.startInAppPayWithCustomSheet(
            samsungPayTransaction.makeTransactionDetailsWithSheet(),
            transactionInfoListener
        )
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
                Toast.makeText(applicationContext, "onSuccess() ", Toast.LENGTH_SHORT).show()
            }

            // This callback is received when the online payment transaction has failed.
            override fun onFailure(errorCode: Int, errorData: Bundle?) {
                Toast.makeText(applicationContext, "onFailure() ", Toast.LENGTH_SHORT).show()
            }
        }


}