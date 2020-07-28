package com.jagruti.todonotesapp.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.jagruti.todonotesapp.R
import com.jagruti.todonotesapp.utils.PrefConstant
import com.jagruti.todonotesapp.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick ,OnBoardingTwoFragment.OnOptionClick{
    lateinit var viewPager : ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
        setupSharedPreference()
    }

    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.ON_BOARDING_SUCESSFULLY, Context.MODE_PRIVATE)

    }

    private fun bindViews() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter=adapter
    }

    override fun onClick() {
        viewPager.currentItem=1
    }

    override fun onOptionBack() {
        viewPager.currentItem=0
    }

    override fun onOptionDone() {
        editor=sharedPreferences.edit()
        editor.putBoolean(PrefConstant.ON_BOARDING_SUCESSFULLY,true)
        editor.apply()
        val intent = Intent(this@OnBoardingActivity,LoginActivity::class.java)
        startActivity(intent)
    }

}
//1st Time
//Splash->OnBoarding->Login->Notes

//2nd Time
//Splash->Login->Notes

//Splash->Notes