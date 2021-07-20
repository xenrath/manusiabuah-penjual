package com.xenrath.manusiabuah.data.database.model

import com.google.gson.annotations.SerializedName

data class ResponseProductUpdate(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)