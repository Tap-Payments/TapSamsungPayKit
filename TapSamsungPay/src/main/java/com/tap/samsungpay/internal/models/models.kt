package com.tap.samsungpay.internal.models

import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.open.enums.*
import company.tap.tapcardvalidator_android.CardBrand
import kotlin.collections.ArrayList


data class TapCustomer(
    var identifier: String,
    var editable: Boolean,
    @SerializedName("email")
    var emailAddress: String,
    @SerializedName("phone")
    var phoneNumber: PhoneNumber,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("middleName")
    var middleName: String,
    @SerializedName("lastName")

    var lastName: String
)

data class PhoneNumber(var isdNumber: String, var phoneNumber: String)

/**
 * 1- Supported Brands should be a list of CardBrand
 * 2- payment_type ->> CARD not ALL
 * 3- attribute to send card Brands is :supported_payment_methods
 */
data class Acceptance(
    val supportedSchemes: MutableList<String> = arrayListOf(),

)




data class AddOns(
    var loader: Boolean = true,
    var displayCardScanning: Boolean = true,
    var showHideNfc: Boolean = true

)
data class Fields(
    var shipping: Boolean = true,
    var billing: Boolean = true,
)

data class TapInterface(var locale: String ?= "en", var edges: Edges,var theme:ThemeMode?=ThemeMode.LIGHT, var colorStyle :String )