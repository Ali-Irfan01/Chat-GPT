package com.example.openai_chatgpt.data.models

data class GPTRequest(
    val model: String = "text-davinci-003",
    var prompt: String = "",
    val temperature: Double = 0.9,
    val max_tokens: Int = 150,
    val top_p: Int = 1,
    val frequency_penalty: Double = 0.0,
    val presence_penalty: Double = 0.6,
    val stop: List<String> = listOf(" Human:", " AI:")
)