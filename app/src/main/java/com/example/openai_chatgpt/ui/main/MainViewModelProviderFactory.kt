package com.example.openai_chatgpt.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openai_chatgpt.data.repository.RepositoryImplementation

class MainViewModelProviderFactory (
    private val repository: RepositoryImplementation
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}