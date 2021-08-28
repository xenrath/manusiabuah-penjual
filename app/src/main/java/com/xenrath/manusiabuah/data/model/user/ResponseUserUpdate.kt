package com.xenrath.manusiabuah.data.model.user

import com.google.gson.annotations.SerializedName

data class ResponseUserUpdate(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)