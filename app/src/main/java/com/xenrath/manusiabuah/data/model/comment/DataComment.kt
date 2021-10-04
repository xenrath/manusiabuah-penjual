package com.xenrath.manusiabuah.data.model.comment

import com.google.gson.annotations.SerializedName
import com.xenrath.manusiabuah.data.model.transaction.DataTransaction

data class DataComment(
    @SerializedName("id") val id: Long?,
    @SerializedName("transaction_id") val transaction_id: String?,
    @SerializedName("comment") val comment: String?,
    @SerializedName("transaction") val transaction: DataTransaction?
)