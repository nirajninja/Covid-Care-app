package com.androiddevs.mvvmnewsapp.preventions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.androiddevs.mvvmnewsapp.MainActivity
import com.androiddevs.mvvmnewsapp.R
import kotlinx.android.synthetic.main.activity_prevention_main.*

class preventionMain : AppCompatActivity() {
    /*   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prevention_main)






    }
*/


    private val introSlideAdapter = IntroSlideAdapter(
        listOf(
            IntroSlide(
                "Wash your hands often",
                "If soap and water are not readily available, use a hand sanitizer that contains at least 60% alcohol. Cover all surfaces of your hands and rub them together until they feel dry.",
                R.drawable.handwash
            ),
            IntroSlide(
                "Cover your mouth and nose with a mask",
                "Everyone should wear a mask in public settings and when around people who don’t live in your household, especially when other social distancing measures are difficult to maintain.",
                R.drawable.mask
            ),

            IntroSlide(
                "Avoid close contact",
                "Put 6 feet of distance between yourself and people who don’t live in your household.",
                R.drawable.socialdistsance
            ),
            IntroSlide(
                "Cover coughs and sneezes",
                "Always cover your mouth and nose with a tissue when you cough or sneeze or use the inside of your elbow and do not spit.",
                R.drawable.socialdistsance
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prevention_main)
        IntroSliderViewpager.adapter = introSlideAdapter
        setupIndicators()
        setCurrentIndicator(0)
        IntroSliderViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)


            }
        })
        buttonNext.setOnClickListener {
            if (IntroSliderViewpager.currentItem + 1 < introSlideAdapter.itemCount) {
                IntroSliderViewpager.currentItem += 1
            } else {

            }
        }

        skipIntro.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )

                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )

            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}