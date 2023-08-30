package com.tap.samsungpay.open

interface SDKDelegate {
    fun onError(error: String?)
    fun onSuccess(token: String)
    fun onCancel()

}

interface InternalCheckoutProfileDelegate {
    fun onError(error: String?)
    fun onSuccess()

}