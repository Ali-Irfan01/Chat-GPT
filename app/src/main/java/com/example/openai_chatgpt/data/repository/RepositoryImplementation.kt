package com.example.openai_chatgpt.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.BuildConfig
import com.example.openai_chatgpt.BuildConfig.MY_API_KEY
import com.example.openai_chatgpt.data.apiInterface.APIClient
import com.example.openai_chatgpt.data.apiInterface.APIInterface
import com.example.openai_chatgpt.data.models.*
import com.example.openai_chatgpt.utils.Utils

class RepositoryImplementation(context: Context) : Repository {

    private val ctx = context
    private val _responseLiveData = MutableLiveData<NetworkResult<GPTResponse>>()
    val responseLiveData: LiveData<NetworkResult<GPTResponse>>
        get() = _responseLiveData

    private val _imageResponseLiveData = MutableLiveData<NetworkResult<GPTImageResponse>>()
    val imageResponseLiveData: LiveData<NetworkResult<GPTImageResponse>>
        get() = _imageResponseLiveData

    override suspend fun getTextResponse(request: GPTRequest){
        Log.d("TAG", "RepositoryImplementation: In repo impl")

        _responseLiveData.postValue(NetworkResult.Loading())

        try {
            if (Utils.isConnected(ctx)) {
                val apiRequest = APIClient.getClient().create(APIInterface::class.java)
                val response = apiRequest.getTextResponse(request = request)
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

    override suspend fun getImageResponse(request: GPTImageRequest) {
        Log.d("TAG", "RepositoryImplementation: In repo impl")

        _imageResponseLiveData.postValue(NetworkResult.Loading())

        try {
            if (Utils.isConnected(ctx)) {
                val apiRequest = APIClient.getClient().create(APIInterface::class.java)
                val response = apiRequest.getImageResponse(request = request)
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        _imageResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
                    } else {
                        Log.d("TAG","RepositoryImplementation: Success in response, error in result")
                        _imageResponseLiveData.postValue(NetworkResult.Error("Invalid Response Codes"))
                    }
                } else {
                    Log.d("TAG", "RepositoryImplementation: Error in response")
                    _imageResponseLiveData.postValue(NetworkResult.Error(response.errorBody()!!.string()))
                }
            } else {
                Log.d("TAG", "RepositoryImplementation: No Internet")
                _imageResponseLiveData.postValue(NetworkResult.Error("No Internet Connection"))
            }
        } catch (e: Exception) {
            Log.d("TAG", "RepositoryImplementation: Error\n${e.message}")
            _imageResponseLiveData.postValue(NetworkResult.Error(e.message ?: "An error occurred"))
        }
    }
}