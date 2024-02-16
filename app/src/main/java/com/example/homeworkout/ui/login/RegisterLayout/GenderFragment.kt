package com.example.homeworkout.ui.login.RegisterLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkout.R
import com.example.homeworkout.databinding.FragmentGenderBinding


class GenderFragment : Fragment() {
    lateinit var binding : FragmentGenderBinding
    val args: GenderFragmentArgs by navArgs()
    var isMale : Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGenderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValue()
        addEvents()
    }

    private fun setValue() {
        isMale = true
        binding.maleBtn.setBackgroundResource(R.drawable.active_circle)
        binding.iconMale.setImageResource(R.drawable.active_male_gender)

        binding.femaleBtn.setBackgroundResource(R.drawable.inactive_circle)
        binding.iconFemale.setImageResource(R.drawable.inactive_female_gender)
    }

    private fun addEvents() {
        binding.maleBtn.setOnClickListener {
            isMale = true
            binding.maleBtn.setBackgroundResource(R.drawable.active_circle)
            binding.iconMale.setImageResource(R.drawable.active_male_gender)

            binding.femaleBtn.setBackgroundResource(R.drawable.inactive_circle)
            binding.iconFemale.setImageResource(R.drawable.inactive_female_gender)
        }
        binding.femaleBtn.setOnClickListener {
            isMale = false
            binding.femaleBtn.setBackgroundResource(R.drawable.active_circle)
            binding.iconFemale.setImageResource(R.drawable.active_female_gender)

            binding.maleBtn.setBackgroundResource(R.drawable.inactive_circle)
            binding.iconMale.setImageResource(R.drawable.inactive_male_gender)

        }
        binding.nextBtn.setOnClickListener {
            val user = args.user
            if(isMale){
                user.gender = "Male"
            }
            else{
                user.gender = "Female"
            }
            val bundle = bundleOf(
                "user" to user
            )
            findNavController().navigate(R.id.action_genderFragment_to_ageFragment,bundle)
        }
    }


}