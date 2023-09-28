package com.grewal.newsapp.data.api

import com.grewal.newsapp.data.entities.NewsResponse
import com.grewal.newsapp.utils.ConstUtils.Companion.api_Key
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET(value= "v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode:String="in",
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String=api_Key

    ):Response<NewsResponse>
    @GET("/v2/top-headlines")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("language") language: String ="en",
        @Query("apiKey")
        apiKey:String=api_Key
    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun searchCategory(
        @Query("category") query: String,
        @Query("language") language: String ="en",
        @Query("apiKey")
        apiKey:String=api_Key
    ): Response<NewsResponse>
}