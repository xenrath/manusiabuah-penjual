package com.xenrath.manusiabuah.data.model.transaction

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.address.DataAddress
import com.xenrath.manusiabuah.data.model.bargain.DataBargain

data class DataTransaction(
    @SerializedName("id") val id: Long?,
    @SerializedName("invoice_number") val invoice_number: String?,
    @SerializedName("bargain_id") val bargain_id: String?,
    @SerializedName("address_id") val address_id: String?,
    @SerializedName("shipping_method") val shipping_method: String?,
    @SerializedName("courier") val courier: String?,
    @SerializedName("service_type") val service_type: String?,
    @SerializedName("note") val note: String? = null,
    @SerializedName("total_transfer") val total_transfer: Int?,
    @SerializedName("payment_status") val payment_status: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("bargain") val bargain: DataBargain?,
    @SerializedName("address") val address: DataAddress?
)