package com.xenrath.manusiabuah.data.model.bargain

import com.google.gson.annotations.SerializedName

data class ResponseBargainList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("bargains") val bargains: List<DataBargain>
)