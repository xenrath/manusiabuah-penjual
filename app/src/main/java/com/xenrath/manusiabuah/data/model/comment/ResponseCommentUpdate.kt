package com.xenrath.manusiabuah.data.model.comment

import com.google.gson.annotations.SerializedName

data class ResponseCommentUpdate(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String?
)