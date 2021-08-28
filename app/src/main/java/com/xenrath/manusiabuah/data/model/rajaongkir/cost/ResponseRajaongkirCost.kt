package com.xenrath.manusiabuah.data.model.rajaongkir.cost

import com.google.gson.annotations.SerializedName

data class ResponseRajaongkirCost(
    @SerializedName("status") val status: Boolean,
    @SerializedName("rajaongkir") val rajaongkir: DataRajaongkirCost
)