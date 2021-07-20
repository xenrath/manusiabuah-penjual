package com.xenrath.manusiabuah.data

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.database.model.DataProduct

data class ResponseProduct(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("product") val product: List<DataProduct>
)