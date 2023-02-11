package com.example.openai_chatgpt.data.apiInterface

import com.example.openai_chatgpt.data.models.GPTRequest
import com.example.openai_chatgpt.data.models.GPTResponse
import com.example.openai_chatgpt.utils.Constants.Companion.URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {

    @POST(URL)
    suspend fun getResponse(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") apiKey: String,
        @Body request: GPTRequest
    ) : Response<GPTResponse>

}