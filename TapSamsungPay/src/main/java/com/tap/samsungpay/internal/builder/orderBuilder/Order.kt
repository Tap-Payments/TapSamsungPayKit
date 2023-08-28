package com.tap.samsungpay.internal.builder.orderBuilder


class Order(
    var identifier: String?
) {


    class Builder {
        private var identifier: String? = null

        fun setIdenitifier(identifier: String?) = apply {
            this.identifier = identifier
        }

        fun build(): Order {
            return Order(identifier)
        }
    }
}