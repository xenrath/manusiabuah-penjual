package com.xenrath.manusiabuah.data.model.rajaongkir.cost

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.rajaongkir.territory.DataStatus

data class DataRajaongkirCost(
    @SerializedName("status") val status: DataStatus,
    @SerializedName("results") val results: List<DataResultsCost>
)