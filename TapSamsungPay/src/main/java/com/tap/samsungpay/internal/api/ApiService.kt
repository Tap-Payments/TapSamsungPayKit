package com.tap.samsungpay.internal.api

object ApiService {
    /**
     * The Base url.
     */
    const val PRODUCTION_URL = "https://api.tap.company/v2/"
    const val BASE_URL = "https://mw-sdk.dev.tap.company/v2/checkout/"

    const val INIT = "checkoutprofile"

    /**
     * The Auth token key.
     */
    const val AUTH_TOKEN_KEY = "Authorization"

    /**
     * The Auth token prefix.
     */
    const val AUTH_TOKEN_PREFIX = "Bearer "

    /**
     * The Application.
     */
    const val APPLICATION = "Application"

    /**
     * The Content type key.
     */
    const val CONTENT_TYPE_KEY = "content-type"

    /**
     * The Content type value.
     */
    const val CONTENT_TYPE_VALUE = "application/json"

    /**
     * The Accept key.
     */
    const val ACCEPT_KEY = "Accept"

    /**
     * The Accept value.
     */
    const val ACCEPT_VALUE = "application/json"


    /**
     * The Token.
     */
    const val TOKEN = "token/"

    /**
     * The Payment types.
     */
    const val PAYMENT_TYPES = "payment/types"
}