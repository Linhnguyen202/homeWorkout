package com.example.homeworkout.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.homeworkout.ui.login.RegisterLayout.RegisterFragment
import com.example.homeworkout.ui.login.SignInLayout.SignInFragment

class LoginViewPager(val fm : FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       when(position){
           0 -> {
               return SignInFragment();
           }
           1 -> {
               return RegisterFragment()
           }
           else -> {
               return SignInFragment();
           }
       }
    }
}