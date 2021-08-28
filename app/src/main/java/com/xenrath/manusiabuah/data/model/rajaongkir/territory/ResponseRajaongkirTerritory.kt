package com.xenrath.manusiabuah.data.model.rajaongkir.territory

import com.google.gson.annotations.SerializedName

data class ResponseRajaongkirTerritory(
    @SerializedName("status") val status: Boolean,
    @SerializedName("rajaongkir") val rajaongkir: DataRajaOngkirTerritory
)