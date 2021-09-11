package com.xenrath.manusiabuah.data.model.transaction

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.address.DataAddress
import com.xenrath.manusiabuah.data.model.product.DataProduct

data class DataTransaction(
    @SerializedName("id") val id: Long?,
    @SerializedName("invoice_number") val invoice_number: String?,
    @SerializedName("product_id") val product_id: String?,
    @SerializedName("address_id") val address_id: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("total_item") val total_item: String?,
    @SerializedName("courier") val courier: String?,
    @SerializedName("service_type") val service_type: String?,
    @SerializedName("cost") val cost: String?,
    @SerializedName("note") val note: String? = null,
    @SerializedName("total_price") val total_price: Int?,
    @SerializedName("proof") val proof: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("product") val product: DataProduct?,
    @SerializedName("address") val address: DataAddress?
)