package com.example.openai_chatgpt.data.models

import com.google.gson.annotations.SerializedName

data class GPTImageResponse(

    @SerializedName("created")
    val created: Double,

    @SerializedName("data")
    val data: List<ImageUrl>
)
