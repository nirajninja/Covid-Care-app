package com.androiddevs.mvvmnewsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androiddevs.mvvmnewsapp.preventions.preventionMain
import com.androiddevs.mvvmnewsapp.symptoms.symptomMain
import com.androiddevs.mvvmnewsapp.tracker.covidmain_activity
import kotlinx.android.synthetic.main.main_front.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_front)

        btn2.setOnClickListener{
            val intent=Intent(this,NewsActivity::class.java)
            startActivity(intent)

        }
        btn3.setOnClickListener {
            val intent=Intent(this,covidmain_activity::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener {
            val intent=Intent(this,preventionMain::class.java)
            startActivity(intent)
        }

        btn4.setOnClickListener {
            val intent=Intent(this,symptomMain::class.java)
            startActivity(intent)
        }
        info.setOnClickListener {
            val intent=Intent(this,infoActivity::class.java)
            startActivity(intent)
        }


    }
}