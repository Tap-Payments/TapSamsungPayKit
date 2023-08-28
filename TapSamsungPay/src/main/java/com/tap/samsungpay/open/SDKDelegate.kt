package com.tap.samsungpay.open

import com.tap.samsungpay.internal.api.responses.Token

interface SDKDelegate {
    fun onSamsungPayToken(token: String)
    fun onTapToken(token: Token)
    fun onFailed(error: String)

}