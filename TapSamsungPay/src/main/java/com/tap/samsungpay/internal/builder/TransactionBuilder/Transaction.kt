package com.tap.samsungpay.internal.builder.TransactionBuilder

import com.tap.samsungpay.internal.api.Shipping
import com.tap.samsungpay.internal.api.Tax


class Transaction(
    var amount: Double?,
    var currency: String?,
    var shipping: Shipping? = null,
    var tax: Tax? = null
) {
    init {

        if (amount.toString().isEmpty())
            throw IllegalArgumentException("Amount must not be null or blank.")

    }


    class Builder {
        private var amount: Double? = null
        private var currency: String? = null
        private var shipping: Shipping? = null
        private var tax: Tax? = null

        fun setAmount(amount: Double?) = apply {
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

        fun build(): Transaction {
            return Transaction(amount, currency, shipping, tax)
        }
    }
}
