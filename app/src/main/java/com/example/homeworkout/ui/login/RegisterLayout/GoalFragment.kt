package com.example.homeworkout.ui.login.RegisterLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkout.R
import com.example.homeworkout.databinding.FragmentGoalBinding


class GoalFragment : Fragment() {
    lateinit var binding : FragmentGoalBinding
    private var data : Array<String> = arrayOf("Gain Weight","Lose weight","Get fitter","Gain more flexible","Learn the basic")
    val args : GoalFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGoalBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        addEvents()

    }

    private fun setData() {
        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(data.size);
        binding.numberPicker.setDisplayedValues(data);
        binding.numberPicker.setValue(5);
    }

    private fun addEvents() {

        binding.nextBtn.setOnClickListener {
            val user = args.user
            user.goal = binding.numberPicker.value.toString()
            val bundle = bundleOf(
                "user" to user
            )
            findNavController().navigate(R.id.action_goalFragment_to_levelFragment,bundle)
        }
    }


}