package com.example.homeworkout.ui.login.RegisterLayout

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homeworkout.MyApplication
import com.example.homeworkout.R
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.databinding.FragmentLevelBinding
import com.example.homeworkout.di.MainComponent
import com.example.homeworkout.di.viewmodel.ViewModelFactory
import com.example.homeworkout.ui.base.BaseFragment
import com.example.homeworkout.ui.login.AuthViewModel
import com.example.homeworkout.ui.login.LoginScreen
import com.example.homeworkout.ui.main.MainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LevelFragment : BaseFragment<FragmentLevelBinding,AuthViewModel>() {
    private var data : Array<String> = arrayOf("Rookie","Beginner","Intermediate","Advance","True beast")
    val args : LevelFragmentArgs by navArgs()

    lateinit var reference : DatabaseReference

    @Inject lateinit var sharePreferenceUtils: sharePreferenceUtils
    override fun layoutRes(): Int = R.layout.fragment_level
    override fun viewModelClass(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun firebaseClass() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
    }

    override fun initValue() {
        reference = db.getReference("User")
        setValuesNumberPicker()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLevelBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
    }

    private fun addEvents() {
        binding.nextBtn.setOnClickListener {
            val user = args.user
            user.level = binding.numberPicker.value.toString()
            addDatatoLocal(user)
            sharePreferenceUtils.saveUser(user, requireContext())
            saveUser(user)
        }
    }

    private fun addDatatoLocal(user : User) {
        CoroutineScope(Dispatchers.IO).launch {
           val a = viewModel.addUser(user)
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),a.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setValuesNumberPicker() {
        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(data.size);
        binding.numberPicker.setDisplayedValues(data);
        binding.numberPicker.setValue(7);
    }

    private fun saveUser(user : User) {
        reference.child(user.id!!).setValue(user).addOnCompleteListener {
            if(it.isSuccessful){
                requireActivity().startActivity(Intent(activity,MainScreen::class.java))
                requireActivity().finish()
            }
            else{
                Toast.makeText(requireContext(),"Failed",Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_LONG).show()
        }

    }


}