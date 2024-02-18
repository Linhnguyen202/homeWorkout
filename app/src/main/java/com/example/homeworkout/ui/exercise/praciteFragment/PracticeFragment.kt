package com.example.homeworkout.ui.exercise.praciteFragment

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.homeworkout.R
import com.example.homeworkout.data.local.room.model.DoneWorkout
import com.example.homeworkout.data.remote.model.Exercise
import com.example.homeworkout.data.remote.model.Workout
import com.example.homeworkout.databinding.FragmentPracticeBinding
import com.example.homeworkout.databinding.RestLayoutBinding
import com.example.homeworkout.ui.exercise.DoneWorkoutViewModel
import com.example.homeworkout.ui.exercise.ExerciseMainScreen
import com.example.homeworkout.ui.login.AuthViewModel
import com.example.homeworkout.ui.main.homeFragment.HomeViewModel
import com.example.homeworkout.util.CalenderUtils
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class PracticeFragment : DaggerFragment() {
    lateinit var binding : FragmentPracticeBinding
    lateinit var pauseBinding : RestLayoutBinding


    var arrayExercise : ArrayList<Exercise>?  = null

    var currentIndex: Int = 0
    var currentPlaying : Boolean = false
    lateinit var countDownTimer : CountDownTimer
    lateinit var countDownTimerPause : CountDownTimer
    private var mTimeLeftInMillis : Long = 20000
    private var mTimeLeftInMillisPause : Long = 20000
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPracticeBinding.inflate(layoutInflater)
        pauseBinding = binding.restLayout
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        getData()
        addEvent()
    }
    private fun initValue() {
        val workout : Workout? = (activity as ExerciseMainScreen).intent.extras!!.getParcelable("data")
        arrayExercise = workout!!.exercise_list as ArrayList<Exercise>?
        // timer in exercise main screen
        binding.timer.format = "%s"
        binding.timer.base = SystemClock.elapsedRealtime()
        binding.timer.start();
    }

    private fun createCountdownTimer () {
        countDownTimer = object  : CountDownTimer(mTimeLeftInMillis,1000){
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountingDownText(mTimeLeftInMillis,"WORKING")
            }
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                pauseTimer()
                binding.playBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                if(currentIndex == arrayExercise!!.size - 1){
                    handleDoneWorkout()
                }
                else{
                    currentIndex++
                    getData()
                    initRestLayout()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleDoneWorkout() {
        val current = CalenderUtils.formattedDate(LocalDate.now())
        val time = SystemClock.elapsedRealtime() - binding.timer.base
        val workout : Workout? = (activity as ExerciseMainScreen).intent.extras!!.getParcelable("data")
        val doneWorkout = DoneWorkout(workout, arrayExercise!!.size.toString(),workout!!.calorie,time,current.toString())
        val bundle = bundleOf(
            "DoneWorkout" to doneWorkout
        )
        findNavController().navigate(R.id.action_practiceFragment_to_doneFragment,bundle)
    }

    private fun initRestLayout() {
        pauseBinding.restContainer.visibility = View.VISIBLE
        Glide.with(this).load(arrayExercise?.get(currentIndex)!!.image_path).into(pauseBinding.nextExBannner)
        pauseBinding.nextExTite.text = arrayExercise?.get(currentIndex)!!.title
        pauseBinding.totalTitle.text = "${currentIndex + 1} / ${arrayExercise!!.size}"
        mTimeLeftInMillisPause = 20000
        initRestTime()
        pauseBinding.skipBtn.setOnClickListener {
            countDownTimerPause.cancel()
            pauseBinding.restContainer.visibility = View.GONE
        }
        pauseBinding.addTimeBtn.setOnClickListener {
            countDownTimerPause.cancel()
            mTimeLeftInMillisPause += 20000
            initRestTime()
        }
    }
    private fun initRestTime(){
        countDownTimerPause = object  : CountDownTimer(mTimeLeftInMillisPause,1000){
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillisPause = millisUntilFinished
                updateCountingDownText(mTimeLeftInMillisPause,"RESTING")
            }
            override fun onFinish() {
                pauseBinding.restContainer.visibility = View.GONE
            }
        }.start()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent() {
        binding.nextBtn.setOnClickListener {
            currentIndex++
            initRestLayout()
            getData()
            pauseTimer()
        }
        binding.toolbar.backBtn.setOnClickListener {
            (activity as ExerciseMainScreen).finish()
        }
        binding.preBtn.setOnClickListener {
            currentIndex--
            getData()
            pauseTimer()
        }
        binding.playBtn.setOnClickListener {
            if(currentPlaying) {
                pauseTimer()
            }
            else{
                startTimer()
            }
        }
        binding.doneBtn.setOnClickListener {
            if(currentIndex == arrayExercise!!.size - 1){
                pauseTimer()
                binding.timer.stop()
                handleDoneWorkout()

            }
            else{
                currentIndex++
                initRestLayout()
                getData()
                pauseTimer()
            }
        }
    }
    private fun startTimer(){
        if(arrayExercise!!.get(currentIndex).type.equals("1")){
            createCountdownTimer()
            countDownTimer.start()
        }
        currentPlaying = true
        binding.playBtn.setImageResource(R.drawable.ic_baseline_pause_24)
    }
    private fun pauseTimer(){
        currentPlaying = false
        countDownTimer.cancel()
        binding.playBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
    }
    private fun updateCountingDownText(mTimeLeftInMillis : Long, type : String) {
        val timeLeftFormatted = createTime(mTimeLeftInMillis)
        if(type.equals("RESTING")){
            pauseBinding.timer.text = timeLeftFormatted
        }
        else if(type.equals("WORKING")){
            binding.timeTxt.text = timeLeftFormatted
        }

    }
    private fun createTime(mTimeLeftInMillis : Long) : String {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        return timeLeftFormatted
    }

    private fun getData() {
        binding.titleEx.text = arrayExercise?.get(currentIndex)!!.title.toString()
        Glide.with(this).load(arrayExercise?.get(currentIndex)!!.image_path).into(binding.banner)
        if(arrayExercise?.get(currentIndex)!!.type.toString().equals("1")){
            updateCountingDownText(arrayExercise?.get(currentIndex)!!.time!!.toLong(),"WORKING")
            mTimeLeftInMillis = arrayExercise?.get(currentIndex)!!.time!!.toLong()
            createCountdownTimer()
            binding.timeTxt.text = createTime(arrayExercise?.get(currentIndex)!!.time!!.toLong())
            binding.doneBtn.visibility = View.GONE
            binding.playBtn.visibility = View.VISIBLE
        }
        else{
            binding.doneBtn.visibility = View.VISIBLE
            binding.playBtn.visibility = View.GONE
            binding.timeTxt.text = "x " + arrayExercise?.get(currentIndex)!!.repeat.toString()
        }

        // update ui with data first or last
        if(currentIndex == 0) {
            binding.preBtn.visibility = View.GONE
            binding.nextBtn.visibility = View.VISIBLE
        }
        else if(currentIndex == arrayExercise!!.size - 1){
            binding.nextBtn.visibility = View.GONE
            binding.preBtn.visibility = View.VISIBLE
        }
        else{
            binding.preBtn.visibility = View.VISIBLE
            binding.nextBtn.visibility = View.VISIBLE
        }
    }


}