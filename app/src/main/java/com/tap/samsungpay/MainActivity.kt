/**
 *   Created by AhlaamK on 10/19/23, 12:33 PM
 *   Copyright (c) 2023 .
 *   Tap Payments All rights reserved.
 *
 */

package com.tap.samsungpay

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chillibits.simplesettings.tool.getPrefStringValue
import com.chillibits.simplesettings.tool.getPrefs
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.tap.samsungpay.internal.api.responses.Token
import com.tap.samsungpay.internal.builder.merchantBuilder.Merchant
import com.tap.samsungpay.internal.builder.publicKeybuilder.Operator
import com.tap.samsungpay.internal.builder.transactionBuilder.OrderDetail
import com.tap.samsungpay.internal.models.Acceptance
import com.tap.samsungpay.internal.models.PhoneNumber
import com.tap.samsungpay.internal.models.Shipping
import com.tap.samsungpay.internal.models.TapCustomer
import com.tap.samsungpay.internal.models.TapInterface
import com.tap.samsungpay.internal.models.Tax
import com.tap.samsungpay.open.TapConfiguration
import com.tap.samsungpay.open.TapSamsungPayDelegate
import com.tap.samsungpay.open.enums.ColorStyle
import com.tap.samsungpay.open.enums.Edges
import com.tap.samsungpay.open.enums.Language
import com.tap.samsungpay.open.enums.Scope
import com.tap.samsungpay.open.enums.ThemeMode
import com.tap.tapsamsungpay.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.Formatter
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class MainActivity : AppCompatActivity(), TapSamsungPayDelegate {

    private lateinit var tapConfiguration: TapConfiguration
    private var postUrl: String = ""
    private lateinit var hashString: String
    private lateinit var textView: TextView
    private lateinit var textView1: TextView
    private lateinit var progressBar: ProgressBar
   var chargeCalled : Boolean = false

    object Hmac {
        fun digest(
            msg: String,
            key: String,
            alg: String = "HmacSHA256"
        ): String {
            val signingKey = SecretKeySpec(key.toByteArray(), alg)
            val mac = Mac.getInstance(alg)
            mac.init(signingKey)

            val bytes = mac.doFinal(msg.toByteArray())
            return format(bytes)
        }

        private fun format(bytes: ByteArray): String {
            val formatter = Formatter()
            bytes.forEach { formatter.format("%02x", it) }
            return formatter.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Required step.
         * Configure SDK with your choice from the given list.
         */
        initConfigurations()
        TapConfiguration.configureSamsungPayWithTapConfiguration(tapConfiguration, this, this)
        textView = findViewById(R.id.textCiew)
        textView1 = findViewById(R.id.textView1)
        progressBar = findViewById(R.id.progress_circular)

    }

    private fun initConfigurations() {
        // Shipping List
        val shippingList : ArrayList<Shipping> = mutableListOf(Shipping(
            (getPrefStringValue("shipNameKey", "Shipping Test")),
            (getPrefStringValue("shipAmntKey", "0.1")).toDouble()
        )) as ArrayList<Shipping>
        // Tax List
        val taxList : ArrayList<Tax> = mutableListOf( Tax(
            (getPrefStringValue("taxNameKey", "Tax Test")),
            (getPrefStringValue("shipAmntKey", "0.1")).toDouble()
        )) as ArrayList<Tax>
        /** Generate HashString**/
        val stringmsg = "x_publickey${
            getPrefStringValue(
                "publicKey",
                "pk_test_kyloCJZi6DcqOsv4t9GxwbRV"
            )
        }x_amount${
            (getPrefStringValue(
                "amountKey",
                "0.2"
            )).toDouble()
        }x_currency${
            (getPrefStringValue(
                "selectedCurrencyKey",
                "USD"
            ))
        }x_transaction${""}x_post$postUrl"

        hashString = Hmac.digest(
            msg = stringmsg, key = getPrefStringValue(
                "secretKey",
                "sk_test_bNgRpokWMylX3CBJ6FOresTq"
            )
        )
        // Log.e("encrypted hashString",hashstring.toString())

        tapConfiguration =
            TapConfiguration.Builder()
                .setOperator(
                    Operator.Builder()
                        .setPublicKey(
                            getPrefStringValue(
                                "publicKey",
                                "pk_test_kyloCJZi6DcqOsv4t9GxwbRV"
                            )
                        )
                        .setHashString(hashString)
                        .build()
                )//**Required**//
                .setMerchant(
                    Merchant.Builder().setId(getPrefStringValue("merchantIdKey", ""))
                        .setGatwayId(getPrefStringValue("gatewayIdKey", "tappayments")).build()
                )//**Required**//
                .setOrders(
                    OrderDetail.Builder()
                        .setAmount((getPrefStringValue("amountKey", "10")).toDouble())
                        .setCurrency((getPrefStringValue("selectedCurrencyKey", "USD")))
                        .setShipping(
                            Shipping(
                                (getPrefStringValue("shipNameKey", "Shipping Test")),
                                (getPrefStringValue("shipAmntKey", "0.1")).toDouble()
                            )
                        ).setTax(
                            Tax(
                                (getPrefStringValue("taxNameKey", "Tax Test")),
                                (getPrefStringValue("shipAmntKey", "0.1")).toDouble()
                            )
                        ) //Optional
                        .setOrderNumber(getPrefStringValue("orderNoKey", "AMZ333")) //**Optional**//

                        .build()
                )
                .setScope(getScope("scopeKey"))
                .setAcceptance(
                    Acceptance(
                        supportedSchemes = getPrefs().getStringSet(
                            "selectedSchemesKey",
                            emptySet<String>()
                        )!!.toMutableList(),
                    )
                )//**Required**//
                .setTapCustomer(getTapCustomer()) //**Required**//
                .setTapInterface(
                    TapInterface(
                        getLanguageMode("selectedLangKey"),
                        getEdges("selectedcardedgeKey"),
                        getThemeMode("selectedthemeKey"), getColorStyle("selectedColorStyleKey")
                    ) //**Optional**//

                )
                .setPackageName(
                    getPrefStringValue(
                        "packageKey",
                        "company.tap.paybutton"
                    )
                )//**Required**//
                .setServiceId(
                    getPrefStringValue(
                        "serviceIdKey",
                        "1cd18649418d46478eb800"
                    )
                )//**Required**//
                .setShipping(
                    shippingList)
                .setTax(taxList)
                .build()

        if(getScope("scopeKey").name.contains("CHARGE")){
            chargeCalled = true
        }

    }


    private fun getTapCustomer(): TapCustomer =
        TapCustomer(
            identifier = "cus_TS012520211349Za012907577",
            editable = true,
            emailAddress = "abcd@gmail.com",
            phoneNumber = PhoneNumber("965", "66175090"),
            firstName = "FirstName",
            middleName = "middlename",
            lastName = "lastname"
        )


    private fun getThemeMode(key: String): ThemeMode? {
        return when (getPrefStringValue(key, ThemeMode.DARK.name)) {
            ThemeMode.DARK.name -> ThemeMode.DARK
            ThemeMode.LIGHT.name -> ThemeMode.LIGHT
            else -> ThemeMode.DARK
        }
    }

    private fun getLanguageMode(key: String): String {
        return when (getPrefStringValue(key, Language.EN.name)) {
            Language.EN.name.toLowerCase() -> Language.EN.name
            Language.AR.name.toLowerCase() -> Language.AR.name
            else -> Language.EN.name
        }
    }

    private fun getEdges(key: String): Edges {

        return when (getPrefStringValue(key, Edges.curved.name)) {
            Edges.curved.name -> Edges.curved
            Edges.flat.name -> Edges.flat
            else -> Edges.curved
        }
    }

    private fun getScope(key: String): Scope {

        return when (getPrefStringValue(key, Scope.SAMSUNG_TOKEN.name)) {
            Scope.SAMSUNG_TOKEN.name -> Scope.SAMSUNG_TOKEN
            Scope.TAP_TOKEN.name -> Scope.TAP_TOKEN
            else -> Scope.SAMSUNG_TOKEN
        }
    }

    private fun getColorStyle(key: String): String {

        return when (getPrefStringValue(key, ColorStyle.colored.name)) {
            ColorStyle.colored.name -> {
                ColorStyle.colored.name
            }

            ColorStyle.monochrome.name -> ColorStyle.monochrome.name
            else -> ColorStyle.colored.name
        }
    }

    private fun customAlertBox(title: String, message: String) {
        // Create the object of AlertDialog Builder class
        val builder = AlertDialog.Builder(this)

        // Set the message show for the Alert time
        builder.setMessage(message)

            // Set Alert Title
            .setTitle(title)

            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            .setCancelable(false)

            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
            .setPositiveButton("Yes") {
                // When the user click yes button then app will close
                    dialog, which ->
                if(title.contains("onTapToken Called")){
                    dialog.dismiss()
                    lifecycleScope.launch {
                        progressBar.visibility = View.VISIBLE
                        val backgroundResult = withContext(Dispatchers.Default) {
                            // The code you would have had in doInBackground.
                            // Last line of withContext lambda should evaluate to your result, what you would have
                            // returned in doInBackground.
                            callChargeAPI(message.toString())
                        }

                        // The code you would have had in onPostExecute. You can use the value of
                        // backgroundResult here.
                        progressBar.visibility = View.GONE

                    }
                }else{
                    dialog.dismiss()
                    finish()
                }



            }
             // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
            .setNegativeButton("No") {
                // If user click no then dialog box is canceled.
                    dialog, which ->
                dialog.cancel()
                finish()
            }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }

    override fun onError(error: String?) {
        println("error>>" + error)
        if (error != null) {
            customAlertBox("error", error)
        }

    }

    override fun onSamsungPayToken(token: String) {
        println("onSamsungPayToken the token>>" + token)
        customAlertBox("onSamsungPayToken", token)


    }

    override fun onReady(readyStatus: String) {
        println("onReady>>" + readyStatus)
    }

    override fun onTapToken(token: Token) {
       var  sb : StringBuilder= java.lang.StringBuilder()
        sb.append('\n' + token.id.toString() +
                '\n' +token.name +
                '\n' +token.currency +
                '\n' +token.card +
                '\n' +token.`object` +
                '\n' +token.client_ip +
                '\n' +token.currency +
                '\n' +token.created +
                '\n' +token.livemode +
                '\n' +token.type +
                '\n' +token.used)

        //textView1.setText("Tap Token is >>>>"+ token.id.toString())
      //  customAlertBox("Tap Token generated", token.id.toString()+ "\n" +"\n"+"Click yes to generate charge")

            /* lifecycleScope.launch {
                 progressBar.visibility = View.VISIBLE
                 val backgroundResult = withContext(Dispatchers.Default) {
                     // The code you would have had in doInBackground.
                     // Last line of withContext lambda should evaluate to your result, what you would have
                     // returned in doInBackground.
                     callChargeAPI(token.id.toString())
                 }

                 // The code you would have had in onPostExecute. You can use the value of
                 // backgroundResult here.
                 progressBar.visibility = View.GONE

             }*/
         customAlertBox("onTapToken Called", token.toString())



    }




    private fun callChargeAPI(token: String) {
        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(interceptor)

        val client = builder
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val MEDIA_TYPE = "application/json".toMediaType()
// PLease use the amount + currency from the demo values
// Please use the Tap Token
// PLease print the charge response in the demo page + auto copy to clibpoard
        var jsonObject:JSONObject = JSONObject()
        var jsonCustomer:JSONObject = JSONObject()
        var phoneObject:JSONObject = JSONObject()
        phoneObject.put("country_code","965")
        phoneObject.put("number","66175090")

        var merchantObject:JSONObject = JSONObject()
        merchantObject.put("id","1124340")

        var postObejct:JSONObject = JSONObject()
        postObejct.put("post","http://your_website.com/post_url")

         var redirectObject:JSONObject = JSONObject()
        redirectObject.put("redirect","http://your_website.com/redirect_url")

        var sourceObjecr:JSONObject = JSONObject()
        sourceObjecr.put("id",token)


        jsonCustomer.put("first_name","test")
        jsonCustomer.put("middle_name","test")
        jsonCustomer.put("last_name","test")
        jsonCustomer.put("email","test@gmail.com")
        jsonCustomer.put("phone",phoneObject)

        jsonObject.put("amount", getPrefStringValue("amountKey",
            "0.2"))

        jsonObject.put("currency", getPrefStringValue(
            "selectedCurrencyKey",
            "KWD"
        ))
        jsonObject.put("customer", jsonCustomer)
        jsonObject.put("merchant", merchantObject)
        jsonObject.put("post", postObejct)
        jsonObject.put("source", sourceObjecr)
        jsonObject.put("redirect", redirectObject)

    //    val requestBody = "\n{\n  \"amount\": 1,\n  \"currency\": \"KWD\",\n  \"customer\": {\n    \"first_name\": \"test\",\n    \"middle_name\": \"test\",\n    \"last_name\": \"test\",\n    \"email\": \"test@test.com\",\n    \"phone\": {\n      \"country_code\": 965,\n      \"number\": 51234567\n    }\n  },\n  \"merchant\": {\n    \"id\": \"1124340\"\n  },\n  \"source\": {\n    \"id\": \"tok_0WX4824149cRuT21uQ7R883\"\n  },\n  \"post\": {\n    \"url\": \"http://your_website.com/post_url\"\n  },\n  \"redirect\": {\n    \"url\": \"http://your_website.com/redirect_url\"\n  }\n}\n"
val requestBody = jsonObject.toString()

        val request = Request.Builder()
            .url("https://api.tap.company/v2/charges/")
            .post(requestBody.toRequestBody(MEDIA_TYPE))
            .header("Authorization", "Bearer sk_test_xliFRQtUrGfMdcCEgO9ohDSw")
            .header("accept", "application/json")
            .header("content-type", "application/json")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Handler(Looper.getMainLooper()).post {
                    // Toast.makeText(this, "callChargeAPI"+json, Toast.LENGTH_LONG).show()
                    textView.setText("Charge Response Failed>>>>"+response)

                }

            }else {
                var json: JSONObject? = JSONObject(response.body?.string())

                Handler(Looper.getMainLooper()).post {

                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val je: JsonElement = JsonParser.parseString(json.toString())
                    val prettyJsonString = gson.toJson(je)
                    Log.e("TAG", "callChargeAPI: " + prettyJsonString)
                    textView.setText("Charge Response Success>>>>" + prettyJsonString)

                }
            }

        }


    }

    override fun onCancel(cancel: String) {
        customAlertBox("onCancel Called", cancel)
    }


}