package com.xenrath.manusiabuah.data.model.offer

import com.google.gson.annotations.SerializedName

data class ResponseOfferUpdate(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?
)