package com.tap.samsungpay.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tap.samsungpay.internal.builder.TapConfiguration
import company.tap.tapcardformkit.internal.api.models.TapCardDataConfiguration

class SamsungPayViewModel : ViewModel() {
      var data: MutableLiveData<TapConfiguration> = MutableLiveData()

    fun setData(data:TapConfiguration){
        this.data.value = data
    }
}