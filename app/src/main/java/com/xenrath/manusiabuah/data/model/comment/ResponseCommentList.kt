package com.xenrath.manusiabuah.data.model.comment

import com.google.gson.annotations.SerializedName

data class ResponseCommentList(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("comments") val comments: List<DataComment>?
)