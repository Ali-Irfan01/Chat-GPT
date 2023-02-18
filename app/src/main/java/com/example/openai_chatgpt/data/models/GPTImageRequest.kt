package com.example.openai_chatgpt.data.models

data class GPTImageRequest(
    var prompt: String = "",
    val n: Int = 2,
    val size: String = "512x512"
)
