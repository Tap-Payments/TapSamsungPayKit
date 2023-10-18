package com.tap.samsungpay.open

interface SDKDelegate {
    fun onError(error: String?)
    fun onSamsungPayToken(token: String)
    fun onReady(readyStatus: String)
    fun onTapToken(token: String)
    fun onCancel()

}

interface InternalCheckoutProfileDelegate {
    fun onError(error: String?)
    fun onSuccess()

}