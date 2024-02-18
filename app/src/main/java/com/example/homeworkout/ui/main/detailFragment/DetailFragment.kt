package com.example.homeworkout.ui.main.detailFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.homeworkout.R
import com.example.homeworkout.data.remote.model.Category
import com.example.homeworkout.data.remote.model.Exercise
import com.example.homeworkout.data.remote.model.Workout
import com.example.homeworkout.databinding.FragmentDetailBinding
import com.example.homeworkout.ui.exercise.ExerciseMainScreen
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.android.support.DaggerFragment


@Suppress("UNREACHABLE_CODE")
class DetailFragment : DaggerFragment() {
   lateinit var binding : FragmentDetailBinding
    lateinit var db : FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var adapter : ExerciseAdapter
    val exerciseArray : ArrayList<Exercise> = ArrayList()
    val args: DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        db = FirebaseDatabase.getInstance()
        reference = db.getReference("Workout")
        adapter = ExerciseAdapter()
        binding.execiseList.adapter = this@DetailFragment.adapter
        initToolbar()
        addEvents()
        addData()
        getData()
    }

    private fun addData() {
        val data = args.Workout
        binding.workoutTile.text = data.title
        Glide.with(this).load(data.image_path).into(binding.imgMeal)
        binding.descriptionTxt.text = data.description
        binding.timeTitle.text = data.time + " mins"
        binding.caloTitle.text = data.calorie + " cal"
    }

    private fun getData() {
        reference.child(args.Workout.workout_type.toString()).child(args.Workout.id.toString()).child("exercises").addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(item  in  snapshot.children){
                        val id = item.child("id").value.toString()
                        val title = item.child("title").value.toString()
                        val instruction = item.child("instruction").toString()
                        val imageString = item.child("image_path").value.toString()
                        val type = item.child("type").value.toString()
                        val time = item.child("time").value.toString()
                        val repeat = item.child("repeat").value.toString()
                        exerciseArray.add(Exercise(id,title,time,repeat,imageString,instruction,type))
                    }
                    adapter.differ.submitList(exerciseArray)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addEvents() {
        binding.startBtn.setOnClickListener {
            val workout = args.Workout
            workout.exercise_list = exerciseArray
            val intent = Intent(activity,ExerciseMainScreen::class.java)
            intent.putExtra("data",workout)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun initToolbar() {
        val data = args.Workout
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
           (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(data.title)
        }
        val primary = ContextCompat.getColor(requireContext(), R.color.main_background)
        binding.collapsingTollbar.setContentScrimColor(primary)
        binding.collapsingTollbar.setCollapsedTitleTextAppearance(R.style.CustomToolbarTheme)


        var isShow = true
        var scrollRange = -1
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                binding.collapsingTollbar.title = data.title
                isShow = true
            } else if (isShow){
                binding.collapsingTollbar.title = " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
               findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}