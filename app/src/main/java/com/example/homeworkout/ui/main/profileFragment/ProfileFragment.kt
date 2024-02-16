package com.example.homeworkout.ui.main.profileFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeworkout.R
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.databinding.FragmentProfileBinding
import com.example.homeworkout.ui.splash.SplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.rezwan.rcalenderlib.ext.debug
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ProfileFragment : DaggerFragment() {
    lateinit var binding : FragmentProfileBinding
    protected lateinit var auth : FirebaseAuth

    @Inject
    lateinit var sharePreferenceUtils : sharePreferenceUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValue()
        addData()
        addEvents()
    }

    private fun addData() {
        val user = sharePreferenceUtils.getUser(requireContext())
        binding.usernameTextView.text = user.name
    }

    private fun initValue() {
        auth = FirebaseAuth.getInstance()
    }

    private fun addEvents() {
        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            sharePreferenceUtils.removeUser(requireContext())
            requireActivity().startActivity(Intent(activity,SplashScreen::class.java))
            requireActivity().finish()
        }
        binding.profleBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment2)
        }
    }

}