package com.tap.samsungpay.internal.builder.merchantBuilder

class Merchant(var id: String? = null, var gatewayId: String? = null) {
    init {

        if (id.isNullOrBlank())
            throw IllegalArgumentException("merchantId must not be null or blank.")

    }

    class Builder {
        var id: String? = null

        fun setId(id: String?) = apply {
            this.id = id
        }

        var gatewayId: String? = null

        fun setGatwayId(gatewayId: String?) = apply {
            this.gatewayId = gatewayId
        }


        fun build(): Merchant {
            return Merchant(id = id, gatewayId = gatewayId)
        }
    }
}
