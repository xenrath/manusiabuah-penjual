package com.xenrath.manusiabuah.data.model.rajaongkir.territory

import com.google.gson.annotations.SerializedName

data class DataRajaOngkirTerritory(
    @SerializedName("status") val status: DataStatus,
    @SerializedName("results") val results: List<DataResultsTerritory>
)