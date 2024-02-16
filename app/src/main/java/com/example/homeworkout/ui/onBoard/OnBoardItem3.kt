package com.example.homeworkout.ui.onBoard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.homeworkout.R
import com.example.homeworkout.databinding.FragmentOnBoardItem3Binding
import com.example.homeworkout.ui.login.LoginScreen
import com.example.homeworkout.ui.main.MainScreen


class OnBoardItem3 : Fragment() {
    lateinit var binding : FragmentOnBoardItem3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardItem3Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startBtn.setOnClickListener {
            (activity as OnBoardScreen).startActivity(Intent(activity,LoginScreen::class.java))
            (activity as OnBoardScreen).finish()
        }
    }


}