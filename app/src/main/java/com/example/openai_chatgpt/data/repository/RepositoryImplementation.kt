package com.example.openai_chatgpt.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openai_chatgpt.data.apiInterface.APIClient
import com.example.openai_chatgpt.data.apiInterface.APIInterface
import com.example.openai_chatgpt.data.models.GPTRequest
import com.example.openai_chatgpt.data.models.GPTResponse
import com.example.openai_chatgpt.data.models.NetworkResult
import com.example.openai_chatgpt.utils.Constants.Companion.MY_API_KEY
import com.example.openai_chatgpt.utils.Utils

class RepositoryImplementation(context: Context) : Repository {

    private val ctx = context
    private val _responseLiveData = MutableLiveData<NetworkResult<GPTResponse>>()
    val responseLiveData: LiveData<NetworkResult<GPTResponse>>
        get() = _responseLiveData

    override suspend fun getResponse(request: GPTRequest){
        Log.d("TAG", "RepositoryImplementation: In repo impl")

        _responseLiveData.postValue(NetworkResult.Loading())

        try {
            if (Utils.isConnected(ctx)) {
                val apiRequest = APIClient.getClient().create(APIInterface::class.java)
                val response = apiRequest.getResponse(apiKey = "Bearer $MY_API_KEY", request = request)
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        _responseLiveData.postValue(NetworkResult.Success(response.body()!!))
                    } else {
                        Log.d("TAG","RepositoryImplementation: Success in response, error in result")
                        _responseLiveData.postValue(NetworkResult.Error("Invalid Response Codes"))
                    }
                } else {
                    Log.d("TAG", "RepositoryImplementation: Error in response")
                    _responseLiveData.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                }
            } else {
                Log.d("TAG", "RepositoryImplementation: No Internet")
                _responseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
            }
        } catch (e: Exception) {
            Log.d("TAG", "RepositoryImplementation: Error\n${e.message}")
            _responseLiveData.postValue(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }
}