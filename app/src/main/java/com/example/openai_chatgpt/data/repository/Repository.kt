package com.example.openai_chatgpt.data.repository

import com.example.openai_chatgpt.data.models.GPTRequest

interface Repository {

    suspend fun getResponse(request: GPTRequest)

}