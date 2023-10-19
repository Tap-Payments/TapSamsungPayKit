/*
 * *
 *  * Created by $AhlaamK on 10/19/23, 12:01 PM
 *  * Copyright (c) 2023 .
 *  * Tap Payments All rights reserved.
 *  *
 *  *
 */

package com.tap.samsungpay.internal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.internal.*
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import company.tap.tapcardformkit.internal.api.enums.AmountModificatorType
import company.tap.tapcardformkit.internal.api.models.AmountModificator
import company.tap.tapcardformkit.internal.api.models.MetaData
import java.io.Serializable
import java.math.BigDecimal


data class OrderObject(
    @SerializedName("amount")
    @Expose
    private var amount: BigDecimal? = null,

    @SerializedName("currency")
    @Expose
    private val currency: String? = null,

    @SerializedName("customer")
    @Expose
    private var customer: TapCustomer? = null,

    @SerializedName("items")
    @Expose

    var items: ArrayList<ItemsModel>? = null,


    @SerializedName("tax")
    @Expose
    private var tax: ArrayList<TaxObject>? = null,


    @SerializedName("merchant")
    @Expose
    private var merchant: Merchant? = null,

    @SerializedName("metadata")
    @Expose
    private var metaData: MetaData? = null,


    )


data class ItemsModel(
    @field:Expose
    @field:SerializedName("product_id")
    val productId: String? = null,

    @field:Expose
    @field:SerializedName(
        "name"
    )
    val name: String? = null,

    @field:Expose
    @field:SerializedName("amount")
    var amount: BigDecimal? = BigDecimal.ONE,

    @field:Expose
    @field:SerializedName("currency")
    val currency: String? = "",

    @field:Expose
    @field:SerializedName("quantity")
    val quantity: BigDecimal? = BigDecimal.ONE,

    @SerializedName("category")
    @Expose
    private var category: Category? = null,

    @SerializedName("discount")
    @Expose val discount: AmountModificator? = null,

    @SerializedName("vendor")
    @Expose
    private val vendor: Vendor? = null,

    @SerializedName("fulfillment_service")
    @Expose
    val fulfillmentService: String? = null,

    @SerializedName("requires_shipping")
    @Expose
    val isRequireShipping: Boolean? = false,

    @SerializedName("item_code")
    @Expose
    val itemCode: String? = null,

    @SerializedName("account_code")
    @Expose
    val accountCode: String? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null,

    @SerializedName("reference")
    @Expose
    private val reference: ReferenceItem? = null,

    @SerializedName("dimensions")
    @Expose
    val dimensions: ItemDimensions? = null,

    @SerializedName("tags")
    @Expose
    val tags: String? = null,

    @SerializedName("meta_data")
    @Expose
    val metaData: MetaData? = null,

    var isExpandedItem: Boolean = false,

    var totalAmount: BigDecimal? = BigDecimal.ONE
) : Serializable {


    fun getCategory(): Category? {
        return category
    }

    @JvmName("getDiscount1")
    fun getDiscount(): AmountModificator? {
        return discount
    }

    fun getVendor(): Vendor? {
        return vendor
    }

    fun getReference(): ReferenceItem? {
        return reference
    }

    /**
     * Gets plain amount.
     *
     * @return the plain amount
     */
    fun getPlainAmount(): BigDecimal? {
        println("  #### getPlainAmount : " + amount)
        println("  #### quantity : " + quantity)
        assert(amount != null)
        totalAmount = amount!!.multiply(quantity)
        return totalAmount
    }

    /**
     * Gets discount amount.
     *
     * @return the discount amount
     */
    fun getDiscountAmount(): BigDecimal? {
        return if (getDiscount() == null) {
            BigDecimal.ZERO
        } else when (getDiscount()!!.getType()) {
            AmountModificatorType.PERCENTAGE -> getPlainAmount()?.multiply(
                getDiscount()!!.getNormalizedValue()
            )
            AmountModificatorType.FIXED -> getDiscount()!!.getValue()
            else -> BigDecimal.ZERO
        }
    }


}


class AuthorizeAction {

}

data class Shipping(var name: String, var amount: Double)
data class Tax(var name: String, var amount: Double)


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

enum class Category {
    /**
     * Sandbox is for testing purposes
     */
    @SerializedName("PHYSICAL_GOODS")
    PHYSICAL_GOODS,

    /**
     * Production is for live
     */
    @SerializedName("DIGITAL_GOODS")
    DIGITAL_GOODS
}

open class AmountModificator(
    @SerializedName("type")
    @Expose var amnttype: AmountModificatorType? = null,

    @SerializedName("value")
    @Expose var bigvalue: BigDecimal? = null
)

class Vendor     //  Constructor is private to prevent access from client app, it must be through inner Builder class only
    (
    @field:Expose @field:SerializedName("id") private val id: String?,
    @field:Expose @field:SerializedName(
        "name"
    ) private val name: String?
) : Serializable

data class ReferenceItem
    (
    @SerializedName("SKU") @Expose var SKU: String? = null,

    @SerializedName("GTIN") @Expose var GTIN: String? = null
) : Serializable {

    init {

        this.SKU = SKU
        this.GTIN = GTIN

    }

}

data class ItemDimensions(
    @SerializedName("weight_type") @Expose
    private var weightType: String? = null,

    @SerializedName("weight")
    @Expose
    private val weight: Double? = null,


    @SerializedName("measurements")
    @Expose
    private val measurements: String? = null,

    @SerializedName("length")
    @Expose
    private val length: Double? = null,

    @SerializedName("width")
    @Expose
    private val width: Double? = null,

    @SerializedName("height")
    @Expose
    private val height: Double? = null
)


data class TaxObject(var name: String, var description: String?, var amount: AmountModificator)

