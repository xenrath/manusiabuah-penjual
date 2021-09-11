package com.xenrath.manusiabuah.data.model.bank

import com.google.gson.annotations.SerializedName

data class ResponseBankList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("banks") val banks: List<DataBank>?
)