package com.tap.samsungpay.internal.api

import androidx.annotation.RestrictTo

/**
 * Created by AhlaamK on 3/23/22.

Copyright (c) 2022    Tap Payments.
All rights reserved.
 **/
@RestrictTo(RestrictTo.Scope.LIBRARY)
sealed class CardViewEvent {
    object InitEvent : CardViewEvent()
    object CreateSamsungPayTokenEvent : CardViewEvent()
}