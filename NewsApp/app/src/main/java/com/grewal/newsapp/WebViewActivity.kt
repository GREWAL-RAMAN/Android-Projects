package com.grewal.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // Initialize the WebView
        webView = findViewById(R.id.webView)

        // Receive the article URL from the intent
        val articleUrl = intent.getStringExtra("article_url")

        if (articleUrl != null) {
            loadArticleUrl(articleUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadArticleUrl(url: String) {
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
    }
}
