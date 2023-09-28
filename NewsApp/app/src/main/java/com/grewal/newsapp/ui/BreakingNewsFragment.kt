package com.grewal.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grewal.newsapp.MainActivity
import com.grewal.newsapp.R
import com.grewal.newsapp.WebViewActivity
import com.grewal.newsapp.adapter.NewsAdapter
import com.grewal.newsapp.databinding.FragmentBreakingNewsBinding
import com.grewal.newsapp.utils.Resource
import com.grewal.newsapp.viewmodel.NewsViewModel


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var searchEditText: EditText



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel
        setupRV()
        getHomeNews()

        searchEditText = view.findViewById(R.id.searchEditText)


        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchEditText.text.toString().trim()
                if (query.isNotEmpty()) {
                    // Trigger the search operation
                    viewModel.searchNews(query)
                }
                true
            } else {
                false
            }
        }


        newsAdapter.setOnClickListener { article ->
            // Handle the button click here, e.g., open the article in a WebView
            openArticleInWebView(article.url)
        }
        // BreakingNewsFragment.kt

        getSearchNews()
        // Initialize the Bottom Navigation Bar
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Set the initial selected item (e.g., Home)
        bottomNavigationView.selectedItemId = R.id.action_home

        // Handle item clicks
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    // Handle Home button click (e.g., load home content)
                    getHomeNews()
                    true
                }

                R.id.action_business -> {
                    // Handle Business button click
                    searchCategory("business")
                    true
                }
                R.id.action_entertainment -> {
                    // Handle Entertainment button click
                    searchCategory("entertainment")
                    true
                }
                R.id.action_sports -> {
                    // Handle Sports button click
                    searchCategory("sports")
                    true
                }
                R.id.action_general -> {
                    // Handle General button click
                    searchCategory("health")
                    true
                }
                else -> false
            }
        }
        getCategoryNews()

    }

    private fun setupRV() {
        newsAdapter = NewsAdapter()
        binding.rv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun openArticleInWebView(articleUrl: String) {
        // Launch WebViewActivity to display the article
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("article_url", articleUrl)
        startActivity(intent)
    }

    private fun getSearchNews()
    {
        viewModel.searchResults.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { resultResponse ->
                        newsAdapter.differ.submitList(resultResponse.articles)
                    }
                }

                is Resource.Error -> {
                    it.message?.let { errorMessage ->
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    // Handle loading state if needed
                }

                else -> {}
            }
        }

    }
    private fun getCategoryNews()
    {
        viewModel.searchCategory.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { resultResponse ->
                        newsAdapter.differ.submitList(resultResponse.articles)
                    }
                }

                is Resource.Error -> {
                    it.message?.let { errorMessage ->
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    // Handle loading state if needed
                }

                else -> {}
            }
        }

    }
    private fun getHomeNews()
    {
        viewModel.breakingNews.observe(viewLifecycleOwner) { it ->

            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    it.message?.let {
                        Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {

                }

                else -> {}
            }
        }
    }
    private fun searchCategory(category: String) {
        // Perform the search with the given category
        viewModel.searchCategory(category)
    }

}