package com.xenrath.manusiabuah.data.model.transaction

import com.google.gson.annotations.SerializedName

data class ResponseTransactionList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("transactions") val transactions: List<DataTransaction>?,
)