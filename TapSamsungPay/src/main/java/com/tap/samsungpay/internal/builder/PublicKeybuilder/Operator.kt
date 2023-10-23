package com.tap.samsungpay.internal.builder.PublicKeybuilder

class Operator(
    var publicKey: String? = null,
    var hashString: String? = null
) {

    init {

        if (publicKey.isNullOrBlank())
            throw IllegalArgumentException("publicKey must not be null or blank.")

        if (hashString.isNullOrBlank())
            throw IllegalArgumentException("hashString must not be null or blank.")
    }

    class Builder {
        private var publicKey: String? = null
        private var hashString: String? = null

        fun setPublicKey(sandBoxKey: String?) = apply {
            this.publicKey = sandBoxKey
        }

        fun setHashString(productionKey: String?) = apply {
            this.hashString = productionKey
        }

        fun build(): Operator {
            return Operator(publicKey, hashString)
        }
    }

}
