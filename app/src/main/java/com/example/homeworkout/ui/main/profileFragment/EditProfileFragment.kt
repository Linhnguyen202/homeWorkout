package com.example.homeworkout.ui.main.profileFragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homeworkout.R
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.databinding.FragmentDailyBinding
import com.example.homeworkout.databinding.FragmentEditProfileBinding
import com.example.homeworkout.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class EditProfileFragment : BaseFragment<FragmentEditProfileBinding,EditProfileViewModel>() {

    override fun layoutRes(): Int = R.layout.fragment_edit_profile

    override fun viewModelClass(): Class<EditProfileViewModel> = EditProfileViewModel::class.java
    override fun firebaseClass() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
    }

    override fun initValue() {
        reference = db.getReference("User")
        builder =AlertDialog.Builder(requireContext())
        binding.toolbar.titleToolbar.text = "My Profile"
        addData()
        addEvens()
    }

    private fun addData() {
        val user = sharePreferenceUtils.getUser(requireContext())
        binding.usernameEditText.setText(user.name)
        binding.ageEditText.setText(user.old)
    }


    lateinit var reference : DatabaseReference
    @Inject
    lateinit var sharePreferenceUtils : sharePreferenceUtils
    lateinit var builder : AlertDialog.Builder

    private fun addEvens() {
        binding.updateBtn.setOnClickListener {
            builder.setTitle("Alert!!")
                .setMessage("Are you sure to update your profile")
                .setCancelable(true)
                .setPositiveButton("Yes", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        updateData()
                    }

                })
                .setNegativeButton("No", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.cancel()
                    }

                }).show()
        }
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateData() = CoroutineScope(Dispatchers.IO).launch {
        val user = sharePreferenceUtils.getUser(requireContext())
        val name = binding.usernameEditText.text.toString()
        val age = binding.ageEditText.text.toString()
        user.name = name
        user.old = age
        val updates = hashMapOf<String, Any>(
            user.id.toString() to user
        )
        try{
           viewModel.updateUser(name,age,user.id)
            reference.updateChildren(updates).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(requireContext(),"Update User Successfully", Toast.LENGTH_LONG).show()
                    sharePreferenceUtils.saveUser(user,requireContext())
                }
            }.await()
        }
        catch (e : FirebaseNetworkException){
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),"Internet Disconnected", Toast.LENGTH_LONG).show()
            }
        }
    }
}





