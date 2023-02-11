package com.example.openai_chatgpt.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openai_chatgpt.data.models.GPTRequest
import com.example.openai_chatgpt.data.models.GPTResponse
import com.example.openai_chatgpt.data.models.NetworkResult
import com.example.openai_chatgpt.data.repository.RepositoryImplementation
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RepositoryImplementation
) : ViewModel() {

    val responseLiveData: LiveData<NetworkResult<GPTResponse>>
        get() = repository.responseLiveData

    suspend fun getResponse(request: GPTRequest) {
        Log.d("TAG", "MainViewModel: In Main ViewModel")
        viewModelScope.launch {
            repository.getResponse(request)
        }
    }
}