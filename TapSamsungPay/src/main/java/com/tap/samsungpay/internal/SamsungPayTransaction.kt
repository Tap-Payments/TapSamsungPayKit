package com.tap.samsungpay.internal

import android.os.Bundle
import com.samsung.android.sdk.samsungpay.v2.SpaySdk
import com.samsung.android.sdk.samsungpay.v2.payment.CustomSheetPaymentInfo
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentInfo
import com.samsung.android.sdk.samsungpay.v2.payment.sheet.AddressControl
import com.samsung.android.sdk.samsungpay.v2.payment.sheet.AmountBoxControl
import com.samsung.android.sdk.samsungpay.v2.payment.sheet.AmountConstants
import com.samsung.android.sdk.samsungpay.v2.payment.sheet.CustomSheet
import com.samsung.android.sdk.samsungpay.v2.payment.sheet.SheetItemType
import com.tap.samsungpay.open.TapConfiguration

private const val AMOUNT_CONTROL_ID = "amountControlId"
private const val PRODUCT_ITEM_ID = "productItemId"
private const val PRODUCT_TAX_ID = "productTaxId"
private const val PRODUCT_SHIPPING_ID = "productShippingId"

class SamsungPayTransaction {
    val tapConfiguration = TapConfiguration
    fun makeTransactionDetailsWithSheet(): CustomSheetPaymentInfo? {
        val extraPaymentInfo = Bundle()
        val customSheet = CustomSheet()

        println("brandList>>>"+brandList)
        customSheet.addControl(makeAmountControl())

        return CustomSheetPaymentInfo.Builder()
            .setMerchantId(tapConfiguration.getTapConfiguration()?.merchant?.gatewayId)
            .setMerchantName(tapConfiguration.getTapConfiguration()?.merchant?.id)
            .setOrderNumber(TapConfiguration.getTapConfiguration()?.orderNumber ?:"")
            // If you want to enter address, please refer to the javaDoc :
            // reference/com/samsung/android/sdk/samsungpay/v2/payment/sheet/AddressControl.html
            .setAddressInPaymentSheet(CustomSheetPaymentInfo.AddressInPaymentSheet.DO_NOT_SHOW)
            .setAllowedCardBrands(brandList)
            .setCardHolderNameEnabled(true)
            .setRecurringEnabled(false)
            .setCustomSheet(customSheet)
            .setExtraPaymentInfo(extraPaymentInfo)
            .build()
    }

    private fun makeAmountControl(): AmountBoxControl {

        /**
         * amountBox from  integration guide
         */
        val amountBoxControl = AmountBoxControl(AMOUNT_CONTROL_ID, tapConfiguration.getTapConfiguration()?.orderDetail?.currency)
        // amountBoxControl.addItem(PRODUCT_ITEM_ID, "Item", 0.1, "")
        tapConfiguration.getTapConfiguration()?.orderDetail?.tax?.amount?.let {
            amountBoxControl.addItem(PRODUCT_TAX_ID, tapConfiguration.getTapConfiguration()?.orderDetail!!.tax?.name,
                it, "")
        }
        tapConfiguration.getTapConfiguration()?.orderDetail?.shipping?.amount?.let {
            amountBoxControl.addItem(PRODUCT_SHIPPING_ID,  tapConfiguration.getTapConfiguration()?.orderDetail!!.shipping?.name,
                it, "")
        }
        tapConfiguration.getTapConfiguration()?.orderDetail?.amount?.let {
            amountBoxControl.setAmountTotal(
                it, AmountConstants.FORMAT_TOTAL_PRICE_ONLY)
        }
        return amountBoxControl

//        with(TapConfiguration.getTapConfiguration()) {
//            val amountBoxControl =
//                AmountBoxControl(AMOUNT_CONTROL_ID, this?.transaction?.currency)
//
//            /**
//             * amount product
//             */
//            this?.transaction?.amount?.let {
//                amountBoxControl.addItem(
//                    PRODUCT_ITEM_ID, "Item",
//                    it, ""
//                )
//            }
//            /**
//             *  product tax name and value cost
//             */
//            this?.transaction?.tax?.amount?.let { taxAmount ->
//                amountBoxControl.addItem(
//                    PRODUCT_TAX_ID, "Tax", taxAmount,
//                    this.transaction.tax?.name.toString()
//                )
//            }
//            /**
//             *  product shipping name and value cost
//             */
//            this?.transaction?.shipping?.amount?.let { shippingAmount ->
//                amountBoxControl.addItem(
//                    PRODUCT_SHIPPING_ID, "Shipping",
//                    shippingAmount, this.transaction.shipping?.name.toString()
//                )
//            }
//            /**
//             *  product total cost
//             *  @TODO : need to check if need to add all previous values or not
//             */
//            this?.transaction?.amount?.let {
//                amountBoxControl.setAmountTotal(
//                    it,
//                    AmountConstants.FORMAT_TOTAL_PRICE_ONLY
//                )
//            }
//            return amountBoxControl
//        }

    }




    private val brandList: ArrayList<SpaySdk.Brand>
        get() {
            val _brandList :  ArrayList<SpaySdk.Brand> =  ArrayList<SpaySdk.Brand>()
          //  with(TapConfiguration.getTapConfiguration()) {
                // var tapBrands = this?.acceptance?.supportedBrands?.map { it.rawValue.replace("_","") }
                val tapBrands = TapConfiguration.getTapConfiguration()?.acceptance?.supportedSchemes
                println("tapBrands are" + tapBrands)


                for (i in tapBrands?.indices!!) {
                   if(_brandList.contains(SpaySdk.Brand.valueOf(tapBrands[i]))){
                       _brandList.add(SpaySdk.Brand.valueOf(tapBrands[i]))

                   }
                }
           // }

            return _brandList
        }

}

/*
 * Make user's transaction details.
 * The merchant app should send PaymentInfo to Samsung Pay via the applicable Samsung Pay SDK API method for the operation
 * being invoked.
 * Upon successful user authentication, Samsung Pay returns the "Payment Info" structure and the result string.
 * The result string is forwarded to the PG for transaction completion and will vary based on the requirements of the PG used.
 * The code example below illustrates how to populate payment information in each field of the PaymentInfo class.
 */
