package com.grewal.newsapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.RoomDatabase
import com.grewal.newsapp.data.database.ArticleDatabase


import com.grewal.newsapp.databinding.ActivityMainBinding
import com.grewal.newsapp.repository.NewsRepository
import com.grewal.newsapp.viewmodel.NewsViewModel
import com.grewal.newsapp.viewmodel.NewsViewModelProvider

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase.invoke(this))
        val viewModelProviderFactory =NewsViewModelProvider(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)


    }
}