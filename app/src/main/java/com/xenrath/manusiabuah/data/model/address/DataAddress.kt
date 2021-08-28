package com.xenrath.manusiabuah.data.model.address

import com.google.gson.annotations.SerializedName

data class DataAddress(
    @SerializedName("id") val id: Long?,
    @SerializedName("user_id") val user_id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("place") val place: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("province_id") val province_id: String?,
    @SerializedName("province_name") val province_name: String?,
    @SerializedName("city_id") val city_id: String?,
    @SerializedName("city_name") val city_name: String?,
    @SerializedName("postal_code") val postal_code: String?,
    @SerializedName("status") val status: Int?,
)