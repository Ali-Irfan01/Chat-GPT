package com.example.openai_chatgpt.data.apiInterface

import com.example.openai_chatgpt.BuildConfig.MY_API_KEY
import com.example.openai_chatgpt.data.models.GPTImageRequest
import com.example.openai_chatgpt.data.models.GPTImageResponse
import com.example.openai_chatgpt.data.models.GPTRequest
import com.example.openai_chatgpt.data.models.GPTResponse
import com.example.openai_chatgpt.utils.Constants.Companion.IMAGE_MODEL_URL
import com.example.openai_chatgpt.utils.Constants.Companion.TEXT_MODEL_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {

    @POST(TEXT_MODEL_URL)
    suspend fun getTextResponse(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") apiKey: String = "Bearer $MY_API_KEY",
        @Body request: GPTRequest
    ) : Response<GPTResponse>

    @POST(IMAGE_MODEL_URL)
    suspend fun getImageResponse(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") apiKey: String = "Bearer $MY_API_KEY",
        @Body request: GPTImageRequest
    ): Response<GPTImageResponse>

}