package com.xenrath.manusiabuah.data.model.bank

import com.google.gson.annotations.SerializedName

data class DataBank(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?
)