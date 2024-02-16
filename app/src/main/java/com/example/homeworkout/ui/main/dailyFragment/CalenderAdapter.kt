package com.example.homeworkout.ui.main.dailyFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkout.R
import com.example.homeworkout.databinding.CalenderCellBinding
import com.example.homeworkout.util.CalenderUtils
import java.time.LocalDate

class CalenderAdapter(val onClick : (Int,LocalDate)->Unit)  : RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder>() {
    val differ = AsyncListDiffer(this, differCallback)
    private var days: ArrayList<LocalDate?>? = ArrayList()
        inner class CalenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CalenderCellBinding.bind(itemView)
        @RequiresApi(Build.VERSION_CODES.O)
        fun inject(position: Int){
            val localDate = days?.get(position)
            if(localDate == null){
                binding.cellDayText.text = ""
            }
            else{
                binding.cellDayText.text = localDate.dayOfMonth.toString()
                if(localDate.equals(CalenderUtils.selectedDate))
                   binding.parentView.setBackgroundColor(Color.LTGRAY);
                else{
                    binding.parentView.setBackgroundResource(R.color.second_background);
                }
            }
            binding.parentView.setOnClickListener {
                onClick.invoke(position,days?.get(position)!!)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calender_cell, parent, false)

        return CalenderViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalenderViewHolder, position: Int) {
        holder.inject(position)
    }

    override fun getItemCount(): Int {
        return days!!.size
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<LocalDate>() {
            override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
                return oldItem == newItem

            }

            override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
                return oldItem == newItem
            }

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public fun updateList(data: ArrayList<LocalDate?>?) {
        if (data != null) {
            this.days = data

        }
        this.notifyDataSetChanged()
    }


}