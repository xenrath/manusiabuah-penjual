package com.xenrath.manusiabuah.data.database.model

import com.google.gson.annotations.SerializedName

data class ResponseProductList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val dataProduct: List<DataProduct>
)