package com.xenrath.manusiabuah.data.model.product

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.user.DataUser

data class DataProduct(
    @SerializedName("id") val id: Long?,
    @SerializedName("user_id") val user_id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("description") val description: String? = null,
    @SerializedName("address") val address: String?,
    @SerializedName("province_id") val province_id: String?,
    @SerializedName("province_name") val province_name: String?,
    @SerializedName("city_id") val city_id: String?,
    @SerializedName("city_name") val city_name: String?,
    @SerializedName("postal_code") val postal_code: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("stock") val stock: Int?,
    @SerializedName("user") val user: DataUser?
)