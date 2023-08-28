package com.tap.samsungpay.internal.builder.TransactionBuilder


class Transaction(
    var amount: Double?,
    var currency: TapCurrency?
) {
    init {

        if (amount.toString().isEmpty())
            throw IllegalArgumentException("Amount must not be null or blank.")

    }


    class Builder {
        private var amount: Double? = null
        private var currency: TapCurrency? = null

        fun setAmount(amount: Double?) = apply {
            this.amount = amount
        }

        fun setCurrency(currency: TapCurrency?) = apply {
            this.currency = currency
        }

        fun build(): Transaction {
            return Transaction(amount, currency)
        }
    }
}

class TapCurrency(isoCode: String) {
    /**
     * Gets iso code.
     *
     * @return the iso code
     */
    val isoCode: String
    init {
        if (isoCode.isEmpty()) {
            this.isoCode = isoCode
        } else {
            val code = isoCode.toLowerCase()
            this.isoCode = code
        }
    }

}