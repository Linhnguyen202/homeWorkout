package com.example.homeworkout.ui.exercise.doneFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.homeworkout.R
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.databinding.FragmentDoneBinding
import com.example.homeworkout.ui.base.BaseFragment
import com.example.homeworkout.ui.exercise.DoneWorkoutViewModel
import com.example.homeworkout.ui.main.MainScreen
import com.example.homeworkout.util.Utils
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DoneFragment : BaseFragment<FragmentDoneBinding,DoneWorkoutViewModel>() {

    private val args: DoneFragmentArgs by navArgs()

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    @Inject lateinit var sharePreferenceUtils: sharePreferenceUtils

    var type : String? = null
    override fun layoutRes(): Int = R.layout.fragment_done

    override fun viewModelClass(): Class<DoneWorkoutViewModel> = DoneWorkoutViewModel::class.java


    override fun firebaseClass() {

    }

    override fun initValue() {

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addData()
        addEvent()
    }

    private fun addEvent() {
        binding.finishBtn.setOnClickListener {
            onSubmit()

        }
        binding.hardImg.setOnClickListener {
            binding.hardImg.setImageResource(R.drawable.hard_active)
            binding.hardContainer.setBackgroundResource(R.drawable.active_circle)

            binding.normalImg.setImageResource(R.drawable.normal_inactive)
            binding.normalContainer.setBackgroundResource(R.drawable.inactive_circle)

            binding.easyImg.setImageResource(R.drawable.easy_inactive)
            binding.easyContainer.setBackgroundResource(R.drawable.inactive_circle)
        }
        binding.normalImg.setOnClickListener {
            binding.normalImg.setImageResource(R.drawable.normal_active)
            binding.normalContainer.setBackgroundResource(R.drawable.active_circle)

            binding.hardImg.setImageResource(R.drawable.hard_inactive)
            binding.hardContainer.setBackgroundResource(R.drawable.inactive_circle)

            binding.easyImg.setImageResource(R.drawable.easy_inactive)
            binding.easyContainer.setBackgroundResource(R.drawable.inactive_circle)
        }
        binding.easyImg.setOnClickListener {
            binding.easyImg.setImageResource(R.drawable.easy_active)
            binding.easyContainer.setBackgroundResource(R.drawable.active_circle)

            binding.normalImg.setImageResource(R.drawable.normal_inactive)
            binding.normalContainer.setBackgroundResource(R.drawable.inactive_circle)

            binding.hardImg.setImageResource(R.drawable.hard_inactive)
            binding.hardContainer.setBackgroundResource(R.drawable.inactive_circle)

        }
    }

    private fun onSubmit() {
        val data = args.DoneWorkout
        data.idUser = sharePreferenceUtils.getUser(requireContext()).id
        CoroutineScope(Dispatchers.IO).launch {
            val a = viewModel.saveDoneWorkout(data)
            withContext(Dispatchers.Main){
                if(a != null){
                    startActivity(Intent(activity, MainScreen::class.java))
                    activity?.finish()
                }
            }
        }

    }

    private fun addData() {
        val data = args.DoneWorkout
        binding.workoutTitle.text = data.workout!!.title
        binding.numberExTitle.text = data.total
        binding.caloTitle.text = data.calorie
        binding.durationTitle.text = Utils.createTime(data.time!!.toLong())
    }

}