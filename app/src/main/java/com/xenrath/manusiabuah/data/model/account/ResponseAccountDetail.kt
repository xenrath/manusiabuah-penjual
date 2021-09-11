package com.xenrath.manusiabuah.data.model.account

import com.google.gson.annotations.SerializedName

data class ResponseAccountDetail(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("account") val account: DataAccount?
)