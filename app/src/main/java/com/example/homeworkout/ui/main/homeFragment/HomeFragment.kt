package com.example.homeworkout.ui.main.homeFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homeworkout.R
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.data.remote.model.Category
import com.example.homeworkout.data.remote.model.Workout
import com.example.homeworkout.databinding.FragmentHomeBinding
import com.example.homeworkout.ui.base.BaseFragment
import com.example.homeworkout.ui.login.AuthViewModel
import com.example.homeworkout.ui.main.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>() {
    lateinit var adapter: CategoryAdapter
    lateinit var workoutAdapter : WorkoutAdapter

    lateinit var warmUpAdapter : WorkoutAdapter

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    override fun layoutRes(): Int = R.layout.fragment_home

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun firebaseClass() {
        db = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    @Inject
    lateinit var sharePreferenceUtils : sharePreferenceUtils
    lateinit var referenceCate : DatabaseReference
    lateinit var referenceWorkout: DatabaseReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
        getData()

    }

    override fun initValue() {
        referenceCate = db.getReference("Category")
        referenceWorkout = db.getReference("Workout")
        adapter = CategoryAdapter(onClickCategory)
        workoutAdapter = WorkoutAdapter(onClickWorkout)
        warmUpAdapter = WorkoutAdapter(onClickWorkout)
        binding.cateList.adapter = this@HomeFragment.adapter
        binding.classicList.adapter = this@HomeFragment.workoutAdapter
        binding.warmUpList.adapter = this@HomeFragment.warmUpAdapter
    }
   private fun addData() {
        val user = sharePreferenceUtils.getUser(requireContext())
        binding.homeTitle.text = "Hello " + user.name
        binding.heightValue.text = user.height + " cm"
        binding.weightValue.text = user.weight + " kg"
    }


   private fun getData() {
        var playlistArray : ArrayList<Category> = ArrayList()
        val workoutArray : ArrayList<Workout> = ArrayList()
        val warmUpArray : ArrayList<Workout> = ArrayList()
        referenceCate.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(item  in  snapshot.children){
                        val id = item.child("id").value.toString()
                        val title = item.child("title").value.toString()
                        val descrip = item.child("description").toString()
                        val imageString = item.child("image_path").value.toString()
                        playlistArray.add(Category(id,title,descrip,imageString))
                    }
                    adapter.differ.submitList(playlistArray)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        referenceWorkout.child("Cadio").addValueEventListener(object : ValueEventListener{
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
                    workoutAdapter.differ.submitList(workoutArray)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

       referenceWorkout.child("Warm-up").addValueEventListener(object : ValueEventListener{
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
                       warmUpArray.add(Workout(id,title,imageString,description,calorie,time,level,type,workout_type))
                   }
                   warmUpAdapter.differ.submitList(warmUpArray)
               }
           }

           override fun onCancelled(error: DatabaseError) {

           }

       })
    }

    val onClickWorkout  : (Workout) -> Unit = {

        val bundle = bundleOf(
            "Workout" to it
        )
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bundle)
    }


    val onClickCategory  : (Category) -> Unit = {
        val bundle = bundleOf(
            "Category" to it
        )
        findNavController().navigate(R.id.action_homeFragment_to_workoutListFragment,bundle)
    }


}