package com.xenrath.manusiabuah.data.database.model

import com.google.gson.annotations.SerializedName

data class DataProduct(

    @SerializedName("id") val id: Long?,
    @SerializedName("user_id") val user_id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("category") val category: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("description") val description: String? = null,
    @SerializedName("address") val address: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("stock") val stock: String?,

)