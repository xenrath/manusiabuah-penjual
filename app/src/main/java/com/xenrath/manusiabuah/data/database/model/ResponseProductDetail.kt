package com.xenrath.manusiabuah.data.database.model

import com.google.gson.annotations.SerializedName

data class ResponseProductDetail(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val product: List<DataProduct>
)