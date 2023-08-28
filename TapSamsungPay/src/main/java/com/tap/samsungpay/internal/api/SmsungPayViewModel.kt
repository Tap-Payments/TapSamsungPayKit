package company.tap.tapcardformkit.internal.api

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tap.samsungpay.internal.api.Repository
import company.tap.tapcardformkit.internal.api.models.CreateTokenCard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
class SmsungPayViewModel : ViewModel() {

    private val repository = Repository()
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    private val liveData = MutableLiveData<Resource<CardViewState>>()


    init {
        compositeDisposable.add(repository.resultObservable
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { liveData.value = Resource.Loading() }
            .doOnTerminate { liveData.value = Resource.Finished() }
            .subscribe(
                { data -> liveData.value = Resource.Success(data) },
                { error -> liveData.value = error.message?.let { Resource.Error(it) } }
            ))

    }

    private fun getInitData(
        smsungPayViewModel: SmsungPayViewModel?,
        _context: Context?,
    ) {
        if (_context != null) {
            this.context = _context
            repository.getInitData(_context, smsungPayViewModel)
        }


    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun processEvent(
        event: CardViewEvent,
        cardDataRequest: CreateTokenCard? = null,
        context: Context? = null,
        activity: AppCompatActivity? = null,
    ) {
        when (event) {
            CardViewEvent.InitEvent -> getInitData(this, context)
            CardViewEvent.CreateTokenEvent -> createTokenWithEncryptedCard(
                cardDataRequest,
                activity
            )
            else -> {}
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun createTokenWithEncryptedCard(
        createTokenWithEncryptedDataRequest: CreateTokenCard?,
        activity: AppCompatActivity?,
    ) {
        //  println("createTokenWithEncryptedDataRequest>>."+createTokenWithEncryptedDataRequest)
        if (createTokenWithEncryptedDataRequest != null) {
//            repository.createTokenWithEncryptedCard(
//                createTokenWithEncryptedDataRequest,
//                tapCardInputView,
//                activity
//            )
        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}