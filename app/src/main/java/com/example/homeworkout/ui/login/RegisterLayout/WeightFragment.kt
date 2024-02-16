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
import com.example.homeworkout.databinding.FragmentWeightBinding
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener


class WeightFragment : Fragment() {
   lateinit var binding : FragmentWeightBinding
   val args : WeightFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeightBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValues()
        addEvents()

    }

    private fun setValues() {
        binding.rulerValue.text = binding.rulerPicker.currentValue.toString()
    }

    private fun addEvents() {

        binding.rulerPicker.currentValue
        binding.rulerPicker.setValuePickerListener(object : RulerValuePickerListener{
            override fun onValueChange(p0: Int) {

            }

            override fun onIntermediateValueChange(p0: Int) {
                binding.rulerValue.text = p0.toString()
            }

        })
        binding.nextBtn.setOnClickListener {
            val user = args.user
            user.weight = binding.rulerValue.text.toString()
            val bundle = bundleOf(
                "user" to user
            )
            findNavController().navigate(R.id.action_weightFragment_to_heightFragment,bundle)
        }
    }


}