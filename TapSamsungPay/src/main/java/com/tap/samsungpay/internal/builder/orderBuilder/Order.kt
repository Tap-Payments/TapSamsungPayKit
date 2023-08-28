package company.tap.tapcardformkit.open.builder.orderBuilder

import company.tap.tapcardformkit.internal.interfaces.TapCurrency

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