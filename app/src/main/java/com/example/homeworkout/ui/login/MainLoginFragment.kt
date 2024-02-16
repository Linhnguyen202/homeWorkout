package com.example.homeworkout.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homeworkout.R
import com.example.homeworkout.databinding.ActivityLoginScreenBinding
import com.example.homeworkout.databinding.FragmentMainLoginBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainLoginFragment : Fragment() {
    lateinit var binding : FragmentMainLoginBinding

    lateinit var loginViewPager: LoginViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewPager = LoginViewPager(requireActivity())
        binding.viewPager.adapter = loginViewPager

        TabLayoutMediator(binding.tabLayout,binding.viewPager) { tab, pos ->
            when(pos) {
                0 -> {
                    tab.text = "Sign In"
                }
                1 -> {
                    tab.text = "Register"
                }
            }
        }.attach()
    }


}