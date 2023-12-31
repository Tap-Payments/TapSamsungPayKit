package com.tap.samsungpay.internal.api.enums

import com.google.gson.annotations.SerializedName

/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
/**
 * The enum Permission.
 */
enum class Permission(val permission: String) {
    /**
     * Pci permission.
     */
    @SerializedName("pci")
    PCI("pci"),

    /**
     * Merchant checkout permission.
     */
    @SerializedName("merchant_checkout")
    MERCHANT_CHECKOUT("merchant_checkout"),

    /**
     * Threedsecure disabled permission.
     */
    @SerializedName("threeDSecure_disabled")
    THREEDSECURE_DISABLED("threeDSecure_disabled"),

    /**
     * kfast enabled permission.
     */
    @SerializedName("kfast")
    KFAST("kfast"),

    /**
     * cardInfo enabled permission.
     */
    @SerializedName("card_info")
    CARD_INFO("card_info"),

}