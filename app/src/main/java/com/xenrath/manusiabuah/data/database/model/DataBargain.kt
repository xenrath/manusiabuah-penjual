package com.xenrath.manusiabuah.data.database.model

import com.google.gson.annotations.SerializedName

data class DataBargain(
    @SerializedName("id") val id: Long?,
    @SerializedName("user_id") val user_id: String?,
    @SerializedName("product_id") val product_id: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("price_offer") val price_offer: String?,
    @SerializedName("total_item") val total_item: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("user_name") val user_name: String? = null,
    @SerializedName("product_name") val product_name: String? = null
)