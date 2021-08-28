package com.xenrath.manusiabuah.data.model.rajaongkir.territory

import com.google.gson.annotations.SerializedName

data class DataResultsTerritory(
    @SerializedName("province_id") val province_id: String?,
    @SerializedName("province") val province: String?,
    @SerializedName("city_id") val city_id: String?,
    @SerializedName("city_name") val city_name: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("postal_code") val postal_code: String?
)