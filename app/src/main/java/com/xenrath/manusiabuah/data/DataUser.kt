package com.xenrath.manusiabuah.data

import com.google.gson.annotations.SerializedName

data class DataUser(

    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("address") val address: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("level") val level: String?

)