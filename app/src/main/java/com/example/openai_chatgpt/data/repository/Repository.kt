package com.example.openai_chatgpt.data.repository

import com.example.openai_chatgpt.data.models.GPTImageRequest
import com.example.openai_chatgpt.data.models.GPTRequest

interface Repository {

    suspend fun getTextResponse(request: GPTRequest)

    suspend fun getImageResponse(request: GPTImageRequest)
}