package com.xenrath.manusiabuah.data.model.transaction

import com.google.gson.annotations.SerializedName

data class ResponseTransactionUpdate(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
)