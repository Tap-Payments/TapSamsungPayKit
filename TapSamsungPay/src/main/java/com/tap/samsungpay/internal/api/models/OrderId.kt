package company.tap.tapcardformkit.internal.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tap.samsungpay.internal.api.responses.BaseResponse

data class OrderId ( @SerializedName("id")
                     @Expose
                     private val id: String? = null): BaseResponse {

    fun getId(): String? {
        return id
    }


}
