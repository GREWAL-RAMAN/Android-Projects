package com.grewal.newsapp.data.entities

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)