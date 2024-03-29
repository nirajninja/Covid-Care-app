package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.Repository.NewsRepository

class NewViewModelProviderFactory(
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun<T : ViewModel?> create(modelClass:Class<T>) : T{
        return NewsViewModel(newsRepository) as T

    }

}