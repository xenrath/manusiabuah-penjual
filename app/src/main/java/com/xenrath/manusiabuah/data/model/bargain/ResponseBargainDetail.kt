package com.xenrath.manusiabuah.data.model.bargain

import com.google.gson.annotations.SerializedName

data class ResponseBargainDetail(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("bargain") val bargain: DataBargain?
)