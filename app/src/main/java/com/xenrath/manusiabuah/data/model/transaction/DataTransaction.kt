package com.xenrath.manusiabuah.data.model.transaction

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.product.DataProduct
import com.xenrath.manusiabuah.data.model.user.DataUser

data class DataTransaction(
    @SerializedName("id") val id: Long?,
    @SerializedName("invoice_number") val invoice_number: String?,
    @SerializedName("user_id") val user_id: String?,
    @SerializedName("product_id") val product_id: String?,
    @SerializedName("recipient") val recipient: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("place") val place: String?,
    @SerializedName("origin") val origin: String?,
    @SerializedName("total_item") val total_item: Int?,
    @SerializedName("price") val price: String?,
    @SerializedName("courier") val courier: String?,
    @SerializedName("service_type") val service_type: String?,
    @SerializedName("estimation") val estimation: String?,
    @SerializedName("cost") val cost: String?,
    @SerializedName("note") val note: String? = null,
    @SerializedName("total_price") val total_price: Int?,
    @SerializedName("proof") val proof: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("user") val user: DataUser?,
    @SerializedName("product") val product: DataProduct?
)