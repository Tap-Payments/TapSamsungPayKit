package com.tap.samsungpay.open

import com.tap.samsungpay.internal.api.responses.Token


interface TapSamsungPayDelegate {
    fun onError(error: String?)
    fun onSamsungPayToken(token: String)
    fun onReady(readyStatus: String)
    fun onTapToken(token: Token)
    fun onCancel(cancel : String)

}

interface InternalCheckoutProfileDelegate {
    fun onError(error: String?)
    fun onSuccess()

}