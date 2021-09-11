package com.xenrath.manusiabuah.data.model.offer

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.user.DataUser

data class DataOffer(
    @SerializedName("id") val id: Long?,
    @SerializedName("user_id") val user_id: String?,
    @SerializedName("product_id") val product_id: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("price_offer") val price_offer: String?,
    @SerializedName("total_item") val total_item: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("product") val product: DataProduct?,
    @SerializedName("user") val user: DataUser?
)