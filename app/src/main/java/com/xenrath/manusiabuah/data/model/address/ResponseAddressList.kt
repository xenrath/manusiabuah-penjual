package com.xenrath.manusiabuah.data.model.address

import com.google.gson.annotations.SerializedName

data class ResponseAddressList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("addresses") val addresses: List<DataAddress>?
)