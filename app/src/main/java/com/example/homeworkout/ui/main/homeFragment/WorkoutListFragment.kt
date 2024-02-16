package com.example.homeworkout.ui.main.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkout.R
import com.example.homeworkout.data.remote.model.Workout
import com.example.homeworkout.databinding.FragmentWorkoutListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.android.support.DaggerFragment


class WorkoutListFragment : DaggerFragment() {
    lateinit var binding : FragmentWorkoutListBinding
    val args : WorkoutListFragmentArgs by navArgs()
     lateinit var db : FirebaseDatabase
    lateinit var referenceWorkout: DatabaseReference
     lateinit var adapter : WorkoutAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkoutListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        getData()
        addEvents()
    }

    private fun addEvents() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getData() {
        val data = args.Category
        binding.toolbar.titleToolbar.text = data.title
        val workoutArray : ArrayList<Workout> = ArrayList()
        referenceWorkout.child(data.title.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(item  in  snapshot.children){
                        val id = item.child("id").value.toString()
                        val title = item.child("title").value.toString()
                        val description = item.child("description").value.toString()
                        val imageString = item.child("image_path").value.toString()
                        val calorie = item.child("calorie").value.toString()
                        val time = item.child("time").value.toString()
                        val level  = item.child("level").value.toString()
                        val type = item.child("type").value.toString()
                        val workout_type = item.child("workout_type").value.toString()
                        workoutArray.add(Workout(id,title,imageString,description,calorie,time,level,type,workout_type))
                    }
                    adapter.differ.submitList(workoutArray)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun initValue() {
        db = FirebaseDatabase.getInstance()
        referenceWorkout = db.getReference("Workout")
        adapter = WorkoutAdapter(onClickWorkout)
        binding.workoutList.adapter = adapter
    }

    val onClickWorkout  : (Workout) -> Unit = {
        val bundle = bundleOf(
            "Workout" to it
        )
        findNavController().navigate(R.id.action_workoutListFragment_to_detailFragment,bundle)
    }


}