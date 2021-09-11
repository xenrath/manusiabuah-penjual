package com.xenrath.manusiabuah.data.model.account

import com.google.gson.annotations.SerializedName

data class ResponseAccountList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("accounts") val accounts: List<DataAccount>?
)