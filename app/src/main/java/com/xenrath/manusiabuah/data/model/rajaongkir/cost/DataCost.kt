package com.xenrath.manusiabuah.data.model.rajaongkir.cost

import com.google.gson.annotations.SerializedName

data class DataCost(
    @SerializedName("value") var value: Int?,
    @SerializedName("etd") var etd: String?,
    @SerializedName("note") var note: String?
)