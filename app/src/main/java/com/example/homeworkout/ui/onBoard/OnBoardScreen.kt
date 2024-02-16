package com.example.homeworkout.ui.onBoard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkout.R
import com.example.homeworkout.databinding.ActivityOnBoardScreenBinding

class OnBoardScreen : AppCompatActivity() {
    lateinit var binding : ActivityOnBoardScreenBinding
    lateinit var viewPagerAdpater: ViewPagerAdpater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewPagerAdpater = ViewPagerAdpater(this)
        binding.itemOnBoard.adapter = viewPagerAdpater
        binding.circle.setViewPager(binding.itemOnBoard)
    }
    
}