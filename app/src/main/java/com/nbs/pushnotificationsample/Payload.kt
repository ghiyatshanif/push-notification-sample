package com.nbs.pushnotificationsample


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("title")
    val title: String?
)