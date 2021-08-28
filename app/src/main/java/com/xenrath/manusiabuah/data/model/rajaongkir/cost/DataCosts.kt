package com.xenrath.manusiabuah.data.model.rajaongkir.cost

import com.google.gson.annotations.SerializedName

data class DataCosts(
    @SerializedName("service") var service: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("cost") var cost: List<DataCost>?,
    @SerializedName("is_active") var is_active: Boolean = false,
)