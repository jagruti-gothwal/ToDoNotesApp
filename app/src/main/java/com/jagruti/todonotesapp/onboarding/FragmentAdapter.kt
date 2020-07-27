@file:Suppress("DEPRECATION")

package com.jagruti.todonotesapp.onboarding

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        //0,1
        return when (position) {
            0 -> {
                OnBoardingOneFragment()
            }
            1 -> {
                OnBoardingTwoFragment()
            }
            else -> {
                throw IllegalStateException("position $position is invalid for this viewpager")
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

}