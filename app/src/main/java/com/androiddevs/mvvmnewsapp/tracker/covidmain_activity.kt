package com.androiddevs.mvvmnewsapp.tracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androiddevs.mvvmnewsapp.R
import com.google.gson.Gson
import android.view.LayoutInflater
import com.androiddevs.mvvmnewsapp.MainActivity
import kotlinx.android.synthetic.main.activity_covidmain_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class covidmain_activity : AppCompatActivity() {
    lateinit var  stateAdapter: StateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covidmain_activity)
        list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header,list,false))
        fetchResults()
        btnhome.setOnClickListener {
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchResults() {
        GlobalScope.launch {
            val response= withContext(Dispatchers.IO){
                Client.api.execute()}
                if(response.isSuccessful){
                    //Log.i("info",response.body?.string())
                    val data= Gson().fromJson(response.body?.string(),Response::class.java)
                    launch(Dispatchers.Main) {
                        bindCombinedData(data.statewise[0])
                        bindStatewiseData(data.statewise.subList(0,data.statewise.size))


                    }
                }

            }
        }

    private fun bindStatewiseData(subList: List<StatewiseItem>) {
        stateAdapter=StateAdapter(subList)
        list.adapter=stateAdapter


    }


    private fun bindCombinedData(data: StatewiseItem) {
        val lastUpdatedTime=data.lastupdatedtime
        val simpleDateFormat=SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

        lastUpdatedTv.text = "Last Updated\n ${getTimeAgo(
            simpleDateFormat.parse(lastUpdatedTime)
        )}"
        confirmedTv.text = data.confirmed

        activeTv.text = data.active
        recoveredTv.text = data.recovered
        deceasedTv.text = data.deaths


    }



    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }

}


