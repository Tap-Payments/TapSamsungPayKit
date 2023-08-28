package com.tap.samsungpay.internal.api

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.tap.samsungpay.internal.api.requests.CreateTokenGPayRequest
import com.tap.samsungpay.internal.api.responses.Token
import com.tap.samsungpay.open.DataConfiguration
import company.tap.tapnetworkkit.controller.NetworkController
import company.tap.tapnetworkkit.enums.TapMethodType
import company.tap.tapnetworkkit.exception.GoSellError
import company.tap.tapnetworkkit.interfaces.APIRequestCallback
import retrofit2.Response

class ApiCallsKitRepository : APIRequestCallback{
    lateinit var tokenResponse: Token
    lateinit var activity: Activity
    @RequiresApi(Build.VERSION_CODES.N)
    fun getGPayTokenRequest(
        _activity: Activity,
       createTokenGPayRequest: CreateTokenGPayRequest
    ) {
        activity =_activity
        val jsonString = Gson().toJson(createTokenGPayRequest)
        NetworkController.getInstance().processRequest(
            TapMethodType.POST, ApiService.TOKEN, jsonString,
            this, CREATE_GPAY_TOKEN_CODE
        )
    }
    override fun onSuccess(responseCode: Int, requestCode: Int, response: Response<JsonElement>?) {
        if (requestCode == CREATE_GPAY_TOKEN_CODE) {
            response?.body().let {
                println("response is"+response)
                tokenResponse = Gson().fromJson(it, Token::class.java)
             // DataConfiguration.getListener()?.onTapToken(tokenResponse)
                activity.finish()
            }
        }
    }

    override fun onFailure(requestCode: Int, errorDetails: GoSellError?) {
        if (requestCode == CREATE_GPAY_TOKEN_CODE) {
            errorDetails?.errorBody.let {

            //    errorDetails?.errorBody?.let { it1 -> DataConfiguration.getListener()?.onError(it1) }
                activity.finish()
            }
        }
    }

    companion object {

        private const val CREATE_GPAY_TOKEN_CODE =1

    }
}