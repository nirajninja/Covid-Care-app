package com.androiddevs.mvvmnewsapp.frag

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.NewsActivity
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class article_news_fragment:Fragment(R.layout.fragment_article) {
    lateinit var viewModel:NewsViewModel

    val args: article_news_fragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        val article = args.article
        webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)

        }

        fab.setOnClickListener {
            viewModel.savedArticle(article)
            Snackbar.make(view,"Article saved Successfully",Snackbar.LENGTH_LONG).show()
        }
    }
}