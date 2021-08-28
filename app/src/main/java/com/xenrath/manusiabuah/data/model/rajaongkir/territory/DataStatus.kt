package com.xenrath.manusiabuah.data.model.rajaongkir.territory

import com.google.gson.annotations.SerializedName

data class DataStatus(
    @SerializedName("code") val code: Int,
    @SerializedName("description") val desription: String
)