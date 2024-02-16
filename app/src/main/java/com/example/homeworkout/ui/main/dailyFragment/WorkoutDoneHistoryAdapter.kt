package com.example.homeworkout.ui.main.dailyFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkout.R
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.remote.model.Exercise
import com.example.homeworkout.databinding.ExerciseItemBinding
import com.example.homeworkout.databinding.WorkoutHistoryItemBinding
import com.example.homeworkout.ui.main.detailFragment.ExerciseAdapter
import com.example.homeworkout.util.Utils
import java.util.Locale

class WorkoutDoneHistoryAdapter : RecyclerView.Adapter<WorkoutDoneHistoryAdapter.WorkoutDoneHistoryViewHolder>() {
    val differ = AsyncListDiffer(this, differCallback)

    inner class WorkoutDoneHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = WorkoutHistoryItemBinding.bind(itemView)
        fun inject(doneWorkout: DoneWorkout){
            binding.title.text = doneWorkout.workout!!.title
            binding.date.text = doneWorkout.date
            binding.durationValue.text = Utils.createTime(doneWorkout.time!!.toLong())
            binding.calorieValue.text = doneWorkout.calorie
            Glide.with(itemView).load(doneWorkout.workout!!.image_path).into(binding.banner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutDoneHistoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.workout_history_item, parent, false)
        return WorkoutDoneHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutDoneHistoryViewHolder, position: Int) {
        holder.inject(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<DoneWorkout>() {
            override fun areItemsTheSame(oldItem: DoneWorkout, newItem: DoneWorkout): Boolean {
                return oldItem == newItem

            }

            override fun areContentsTheSame(oldItem: DoneWorkout, newItem: DoneWorkout): Boolean {
                return oldItem == newItem
            }

        }
    }


}