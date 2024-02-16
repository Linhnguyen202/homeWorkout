package com.example.homeworkout.ui.main.homeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkout.R
import com.example.homeworkout.data.remote.model.Category
import com.example.homeworkout.data.remote.model.Workout
import com.example.homeworkout.databinding.CategoryItemBinding
import com.example.homeworkout.databinding.WorkoutItemBinding

class WorkoutAdapter(val onClick : (Workout) -> Unit) :  RecyclerView.Adapter<WorkoutAdapter.WorkoutHolder>() {
    val differ = AsyncListDiffer(this, differCallback)

    inner class WorkoutHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = WorkoutItemBinding.bind(itemView)
        fun inject(workout: Workout){
            if(workout.type == "1"){
                binding.numberEx.text = workout.time + " mins"
            }
            binding.workoutTitle.text = workout.title
            Glide.with(itemView).load(workout.image_path).into(binding.banner)
            binding.banner.setOnClickListener {
                onClick.invoke(workout)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.workout_item, parent, false)
        return WorkoutHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
        holder.inject(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Workout>() {
            override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem == newItem

            }

            override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem == newItem
            }

        }
    }


}