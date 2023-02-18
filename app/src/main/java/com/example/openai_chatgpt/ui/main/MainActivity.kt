package com.example.openai_chatgpt.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.openai_chatgpt.R
import com.example.openai_chatgpt.data.models.*
import com.example.openai_chatgpt.data.repository.RepositoryImplementation
import com.example.openai_chatgpt.databinding.ActivityMainBinding
import com.example.openai_chatgpt.utils.Constants.Companion.myPrompt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = RepositoryImplementation(this)
        val viewModelProviderFactory = MainViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]
        Log.d("TAG", "MainActivity: ViewModel Instantiated")

        binding.getResponse.setOnClickListener {
            hideKeyboard()
            setOnClickListener()
        }

        binding.btnFetchImage.setOnClickListener {
            setOnClickListenerForImage()
        }
    }

    private fun setOnClickListenerForImage()    {
        val myRequest = GPTImageRequest().apply {
            prompt = binding.edtQuestion.text.toString()
        }
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.getImageResponse(myRequest)
        }
        observeLiveDataForImage()
    }

    private fun observeLiveDataForImage() {
        viewModel.responseImageLiveData.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("TAG", "MainActivity: Response received from livedata")
                    Log.d("TAG", "MainActivity: Request Success")
                    setSuccessUiForImage(it.data!!.data.get(0).url)
                }
                is NetworkResult.Error -> {
                    Log.d("TAG", "MainActivity: Request Error")
                    setErrorUiForImage(it)
                }
                is NetworkResult.Loading -> {
                    Log.d("TAG", "MainActivity: Loading")
                    setLoadingUi()
                }
            }
        }
    }

    private fun setErrorUiForImage(it: NetworkResult<GPTImageResponse>){
        binding.GPTImage.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setSuccessUiForImage(url: String){
        Glide.with(this).load(url).into(binding.GPTImage)
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setOnClickListener(){
        Log.d("TAG", "MainActivity: Button Click Listened")
        val myRequest = GPTRequest().apply {
            prompt = "$myPrompt\n\nHuman: ${binding.edtQuestion.text} \nAI:"
        }

        if(binding.edtQuestion.text.toString().isEmpty()){
            setEmptyUI()
        }   else {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getResponse(myRequest)
            }
        }

        observeLiveData()
    }
    private fun setEmptyUI()    {
        binding.progressBar.visibility = View.INVISIBLE
        binding.textView.visibility = View.INVISIBLE
    }
    private fun setSuccessUi(response: String?)  {
        binding.textView.text = response
        binding.edtQuestion.setText("")
        binding.textView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }
    private fun setErrorUi(it: NetworkResult<GPTResponse>)    {
        binding.progressBar.visibility = View.INVISIBLE
        binding.textView.text = it.message
    }
    private fun setLoadingUi()  {
        binding.progressBar.visibility = View.VISIBLE
        binding.textView.visibility = View.INVISIBLE
    }
    private fun observeLiveData()   {
        viewModel.responseLiveData.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("TAG", "MainActivity: Response received from livedata")
                    Log.d("TAG", "MainActivity: Request Success")
                    setSuccessUi(it.data!!.choices.first().text)
                }
                is NetworkResult.Error -> {
                    Log.d("TAG", "MainActivity: Request Error")
                    setErrorUi(it)
                }
                is NetworkResult.Loading -> {
                    Log.d("TAG", "MainActivity: Loading")
                    setLoadingUi()
                }
            }
        }
    }
    private fun hideKeyboard() {
        val activityView = this.window.decorView
        val imm = activityView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activityView.windowToken, 0)
    }
}