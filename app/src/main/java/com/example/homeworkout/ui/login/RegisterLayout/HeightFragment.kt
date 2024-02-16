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
import com.example.homeworkout.databinding.FragmentHeightBinding


class HeightFragment : Fragment() {
   lateinit var binding : FragmentHeightBinding
   val args : WeightFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHeightBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
    }

    private fun addEvents() {
        binding.nextBtn.setOnClickListener {
            val user = args.user
            user.height = binding.numberPicker.value.toString()
            val bundle = bundleOf(
                "user" to user
            )
            findNavController().navigate(R.id.action_heightFragment_to_goalFragment,bundle)
        }
    }


}