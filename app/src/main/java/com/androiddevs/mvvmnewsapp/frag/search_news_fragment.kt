package com.androiddevs.mvvmnewsapp.frag

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.NewsActivity
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class search_news_fragment : Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel:NewsViewModel
    val TAG="SearchNewsFrgament"

    lateinit var newsAdapter : NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()


        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)

            }
            findNavController().navigate(
                R.id.action_search_news_fragment_to_article_news_fragment,
                bundle
            )
        }

        var job: Job?=null
        etSearch.addTextChangedListener {editable ->
            job?.cancel()
            job= MainScope().launch{
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }

            }
        }
        viewModel.searchNews.observe(viewLifecycleOwner, androidx.lifecycle.Observer {response->
            when(response){
                is Resource.Success-> {
                    hideProgressBar()
                    response.data?.let{newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{message->
                        Log.e(TAG,"An error occured: $message")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }

        })

    }


    private fun hideProgressBar()
    {
        paginationProgressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar()
    {
        paginationProgressBar.visibility=View.VISIBLE
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}