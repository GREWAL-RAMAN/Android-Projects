package com.grewal.newsapp.repository



import com.grewal.newsapp.data.database.ArticleDatabase
import com.grewal.newsapp.data.entities.NewsResponse
import com.grewal.newsapp.data.instance.RetrofitInstance
import retrofit2.Response

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode : String , pageNumber : Int) : Response<NewsResponse>
    {
       return RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
    }
    // NewsRepository.kt

    // Add a new suspend function for searching news articles
    suspend fun searchNews(query: String): Response<NewsResponse> {
        return RetrofitInstance.api.searchNews(query)
    }

    suspend fun searchCategory(category: String): Response<NewsResponse> {
        return RetrofitInstance.api.searchCategory(category)
    }


}