package com.xenrath.manusiabuah.data.model.account

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.bank.DataBank

data class DataAccount(
    @SerializedName("id") val id: Long?,
    @SerializedName("user_id") val user_id: String?,
    @SerializedName("bank_id") val bank_id: String?,
    @SerializedName("bank_name") val bank_name: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("number") val number: String?,
    @SerializedName("bank") val bank: DataBank?
)