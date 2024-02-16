package com.example.homeworkout.ui.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homeworkout.R
import com.example.homeworkout.databinding.FragmentOnBoardItem1Binding


class OnBoardItem1 : Fragment() {
    lateinit var binding : FragmentOnBoardItem1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardItem1Binding.inflate(layoutInflater)
        return binding.root
    }


}