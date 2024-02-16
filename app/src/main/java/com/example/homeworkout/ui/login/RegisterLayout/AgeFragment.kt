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
import com.example.homeworkout.databinding.FragmentAgeBinding

class AgeFragment : Fragment() {
  lateinit var binding : FragmentAgeBinding
  val args : AgeFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAgeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvenst()

    }

    private fun addEvenst() {
        binding.nextBtn.setOnClickListener {
            val user = args.user
            user.old = binding.numberPicker.value.toString()
            val bundle = bundleOf(
                "user" to user
            )
            findNavController().navigate(R.id.action_ageFragment_to_weightFragment,bundle)
        }

    }


}