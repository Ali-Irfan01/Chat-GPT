package com.example.openai_chatgpt.data.models

import com.google.gson.annotations.SerializedName

data class GPTResponse(
    @SerializedName("id") val id: String,
    @SerializedName("choices") val choices: List<Choices>
)
