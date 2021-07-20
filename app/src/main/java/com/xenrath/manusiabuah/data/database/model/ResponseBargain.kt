package com.xenrath.manusiabuah.data.database.model

import com.google.gson.annotations.SerializedName

data class ResponseBargain(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("bargain") val bargain: DataBargain?
)
