package com.example.openai_chatgpt.data.apiInterface

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ErrorInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            val bodyString = response.body!!.string()
            return response.newBuilder()
                .body(bodyString.toResponseBody(response.body?.contentType()))
                .build()
        }   catch (e: Exception){
            Log.d("Error",e.message.toString())
        }
        return Response.Builder().body(null).build()
    }
}