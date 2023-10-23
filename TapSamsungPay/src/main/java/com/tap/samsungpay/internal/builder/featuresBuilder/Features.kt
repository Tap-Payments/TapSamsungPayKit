package com.tap.samsungpay.internal.builder.featuresBuilder

class Features(
    var acceptanceBadge: Boolean?
) {


    class Builder {
        private var acceptanceBadge: Boolean? = null

        fun setAcceptanceBadge(acceptanceBadge: Boolean?) = apply {
            this.acceptanceBadge = acceptanceBadge
        }

        fun build(): Features {
            return Features(acceptanceBadge)
        }
    }
}