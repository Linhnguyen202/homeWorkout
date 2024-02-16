package com.example.homeworkout.ui.main.detailFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkout.R
import com.example.homeworkout.data.remote.model.Exercise
import com.example.homeworkout.data.remote.model.Workout
import com.example.homeworkout.databinding.ExerciseItemBinding
import com.example.homeworkout.databinding.WorkoutItemBinding
import java.util.Locale

class ExerciseAdapter : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {
    val differ = AsyncListDiffer(this, differCallback)

    inner class ExerciseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ExerciseItemBinding.bind(itemView)
        fun inject(exercise: Exercise){
            if(exercise.type.equals("1")){
                val minutes = (exercise.time!!.toLong() / 1000).toInt() / 60
                val seconds = (exercise.time!!.toLong() / 1000).toInt() % 60
                val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                binding.execiseTime.text = timeLeftFormatted
            }
            else{
                binding.execiseTime.text = "x "+ exercise.repeat
            }
            binding.execiseTitle.text = exercise.title

            Glide.with(itemView).load(exercise.image_path).into(binding.bannerEx)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.inject(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem == newItem

            }

            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem == newItem
            }

        }
    }


}