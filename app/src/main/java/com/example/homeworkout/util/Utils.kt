package com.example.homeworkout.util

import java.util.Locale

object Utils {
    fun createTime(mTimeLeftInMillis : Long) : String {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        return timeLeftFormatted
    }
    fun getMonth(month : Int)  : String {
        when(month) {
            1 -> {
                return "January"
            }
            2 -> {
                return "February"
            }
            3 -> {
                return "March"
            }
            4 -> {
                return "April"
            }
            5 -> {
                return "May"
            }
            6 -> return "June"
            7 -> return  "July"
            8 -> return "August"
            9 -> return "September"
            10 -> return "October"
            11 -> return "November"
            12 -> return "December"
        }
        return "January"
    }
}