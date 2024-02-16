package com.example.homeworkout.ui.onBoard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdpater(val fm : FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                return OnBoardItem1()
            }
            1 -> {
                return OnBoardItem2()
            }
            2 -> {
                return OnBoardItem3()
            }
            else -> {
                return OnBoardItem1()
            }

        }
    }
}