package com.xenrath.manusiabuah.data.model.address

import com.google.gson.annotations.SerializedName

data class ResponseAddressDetail(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("address") val address: DataAddress?
)