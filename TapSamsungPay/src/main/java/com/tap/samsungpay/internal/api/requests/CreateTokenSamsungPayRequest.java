/**
 *   Created by AhlaamK on 10/19/23, 10:56 AM
 *   Copyright (c) 2023 .
 *   Tap Payments All rights reserved.
 *
 **/

package com.tap.samsungpay.internal.api.requests;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateTokenSamsungPayRequest implements Serializable {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("token_data")
    @Expose
    private  TokenData tokenData;

    public CreateTokenSamsungPayRequest( TokenData tokenData , String type) {
        this.tokenData = tokenData;
        this.type = type;
    }

    /**
     * The type Builder.
     */
    public final static class Builder {
        private CreateTokenSamsungPayRequest createTokenSamsungPayRequest;

        /**
         * Instantiates a new Builder.
         *
         * @param type  the type
         * @param tokenData the tokenData
         */
        public Builder( TokenData tokenData, String type) {
            createTokenSamsungPayRequest = new CreateTokenSamsungPayRequest(tokenData,type);
        }

        /**
         * Build create otp verification request.
         *
         * @return the createTokenGPayRequest request
         */
        public CreateTokenSamsungPayRequest build() {
            return createTokenSamsungPayRequest;
        }
    }







}
