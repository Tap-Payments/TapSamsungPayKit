package com.tap.samsungpay.internal.builder

class AuthKey(
    var sandBoxKey: String? = null,
    var productLiveKey: String? = null
) {
    init {

        if (sandBoxKey.isNullOrBlank())
            throw IllegalArgumentException("Sandbox Key must not be null or blank.")

    }

    class Builder {
        var sandBoxKey: String? = null
        var productLiveKey: String? = null
        fun setSandBox(sandBoxKey: String?) = apply {
            this.sandBoxKey = sandBoxKey
        }

        fun setProductionLiveKey(productLiveKey: String?) = apply {
            this.productLiveKey = productLiveKey
        }


        fun build(): AuthKey {
            return AuthKey(sandBoxKey = sandBoxKey, productLiveKey = productLiveKey)
        }
    }
}
