package com.xenrath.manusiabuah.data.model.transaction

import com.google.gson.annotations.SerializedName

data class ResponseTransactionDetail(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("transaction") val transaction: DataTransaction?,
)