/**
 *  Created by AhlaamK on 10/19/23, 11:06 AM
 *  Copyright (c) 2023 .
 * Tap Payments All rights reserved.
 * *
 */

package com.tap.samsungpay.internal.api.requests;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TokenData implements Serializable {
    @SerializedName("data")
    @Expose
    private JsonElement data;

    public TokenData(JsonElement data ) {
        this.data = data;
    }
}
