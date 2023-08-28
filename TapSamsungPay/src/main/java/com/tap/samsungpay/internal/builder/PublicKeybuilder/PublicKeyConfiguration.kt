package company.tap.tapcardformkit.open.builder.PublicKeybuilder

class PublicKeyConfiguration(
    var sandBoxKey: String? = null,
    var productionKey: String? = null
) {

    init {

        if (sandBoxKey.isNullOrBlank())
            throw IllegalArgumentException("sandboxKey must not be null or blank.")

        if (productionKey.isNullOrBlank())
            throw IllegalArgumentException("productionKey must not be null or blank.")
    }

    class Builder {
        private var sandBoxKey: String? = null
        private var productionKey: String? = null

        fun setSandBoxKey(sandBoxKey: String?) = apply {
            this.sandBoxKey = sandBoxKey
        }

        fun setProductionKey(productionKey: String?) = apply {
            this.productionKey = productionKey
        }

        fun build(): PublicKeyConfiguration {
            return PublicKeyConfiguration(sandBoxKey, productionKey)
        }
    }

}
