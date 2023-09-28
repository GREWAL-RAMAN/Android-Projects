package com.grewal.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grewal.newsapp.data.entities.NewsResponse
import com.grewal.newsapp.repository.NewsRepository
import com.grewal.newsapp.utils.Resource
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {
    val breakingNews :MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage =1

    init {
         getBreakingNews("in")
    }

    fun getBreakingNews(countryCode:String)= viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)

        breakingNews.postValue(handlerBreakingNews(response))
    }
    private fun handlerBreakingNews(response : Response<NewsResponse>): Resource<NewsResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message().toString())
    }
    // NewsViewModel.kt

    // Add a new LiveData for search results
    val searchResults: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    // Add a function for searching news articles
    fun searchNews(query: String) = viewModelScope.launch {
        searchResults.postValue(Resource.Loading())
        val response = newsRepository.searchNews(query)
        searchResults.postValue(handlerBreakingNews(response))
    }
    val searchCategory: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    fun searchCategory(category: String)=viewModelScope.launch {
        searchCategory.postValue(Resource.Loading())
        val response = newsRepository.searchCategory(category)
        searchCategory.postValue(handlerBreakingNews(response))
    }


}