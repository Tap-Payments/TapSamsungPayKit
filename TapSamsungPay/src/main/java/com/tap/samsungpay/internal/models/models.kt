package company.tap.tapcardformkit.open.models

import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.open.enums.*
import company.tap.cardinputwidget.CardBrand
import company.tap.tapcardformkit.open.enums.*
import company.tap.tapcardvalidator_android.CardBrand
import kotlin.collections.ArrayList


data class TapCustomer(
    var identifier: String,
    var nameOnCard: String = "Card Holder Name",
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
    val supportedBrands: ArrayList<CardBrand> = arrayListOf(),
    val supportedFundSource: SupportedFundSource = SupportedFundSource.CREDIT,
    val supportedPaymentAuthentications: SupportedPaymentAuthentications = SupportedPaymentAuthentications.ThreeDS,
)

data class Fields(var cardHolder: Boolean = true)

data class AddOns(
    var loader: Boolean = true,
    var displayCardScanning: Boolean = true,
    var showHideNfc: Boolean = true

)

data class TapInterface(var locale: String, var edges: Edges,theme:Theme)