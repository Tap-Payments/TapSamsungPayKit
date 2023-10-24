package com.tap.samsungpay.internal.builder.transactionBuilder

import com.tap.samsungpay.internal.models.Shipping
import com.tap.samsungpay.internal.models.Tax


class OrderDetail(
    var amount: Double?,
    var currency: String?,
    var shipping: Shipping? = null,
    var tax: Tax? = null,
    var orderNumber: String? = null
) {
    init {

        if (amount.toString().isEmpty())
            throw IllegalArgumentException("Amount must not be null or blank.")


        if (currency.toString().isEmpty())
            throw IllegalArgumentException("Currency must not be null or blank.")

    }


    class Builder {
        private var amount: Double =0.0
        private var currency: String? = null
        private var shipping: Shipping? = null
        private var tax: Tax? = null
        private var orderNumber: String? = null

        fun setAmount(amount: Double) = apply {
            this.amount = amount
        }

        fun setShipping(shipping: Shipping?) = apply {
            this.shipping = shipping
        }

        fun setTax(tax: Tax?) = apply {
            this.tax = tax
        }


        fun setCurrency(currency: String?) = apply {
            this.currency = currency
        }
        fun setOrderNumber(orderNumber: String?) = apply {
            this.orderNumber = orderNumber
        }

        fun build(): OrderDetail {
            return OrderDetail(amount, currency, shipping, tax)
        }
    }
}
