package com.example.homeworkout.ui.login.RegisterLayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.homeworkout.R
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.databinding.FragmentRegisterBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class RegisterFragment : Fragment() {
    lateinit var binding : FragmentRegisterBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseDatabase
    lateinit var reference : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        reference = db.getReference("User")
        addEvents()

    }

    private fun addEvents() {
        binding.usernameEditText.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                binding.usernameContainer.error = ""
                binding.usernameContainer.isErrorEnabled = false
            }
        }
        binding.emailEditText.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                binding.emailContainer.error = ""
                binding.emailContainer.isErrorEnabled = false
            }
        }
        binding.passEditText.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                binding.passContainer.error = ""
                binding.passContainer.isErrorEnabled = false
            }
        }
        binding.registerTitle.setOnClickListener {
            onSubmit()
        }
    }

    private fun validateUsername() : Boolean {
        val username = binding.usernameEditText.text.toString()
        var error : String? = null
        if(username.isNullOrEmpty()){
            error = "Email is required"
        }
        if(error != null){
            binding.usernameContainer.error = error
            binding.usernameContainer.isErrorEnabled = true

        }
        return error != null
    }
    private fun validateEmail() : Boolean {
        val email = binding.emailEditText.text.toString()
        var error : String? = null
        // kiểm tra email
        if(email.isNullOrEmpty()){
            error = "Email is required"
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            error = "Email is not valid"
        }
        if(error != null){
            binding.emailContainer.error = error
            binding.emailContainer.isErrorEnabled = true

        }
        return error != null
    }

    private fun validatePassword() : Boolean {
        val pass = binding.passEditText.text.toString()
        var error : String? = null
        if(pass.isNullOrEmpty()){
            error = "Password is required"
        }
        else if(pass.length < 6){
            error = "Password at least 6 characters"
        }
        if(error != null){
            binding.passContainer.error = error
            binding.passContainer.isErrorEnabled = true

        }
        return error != null
    }

    private fun onValidate() : Boolean{
        var validateValue = false;
        if(validateUsername()) validateValue = true
        if(validateEmail()) validateValue = true
        if(validatePassword()) validateValue = true
        return validateValue
    }
    private fun onSubmit(){
        if(!onValidate()){
            val email = binding.emailEditText.text.toString()
            val password = binding.passEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            // tạo profile
            val profileUser = UserProfileChangeRequest.Builder()
                .setDisplayName(username.toString()) // tên
                .build()
            try {
                CoroutineScope(Dispatchers.IO).launch{
                    withContext(Dispatchers.Main) {
                        binding.progessBar.visibility = View.VISIBLE
                        binding.registerTitle.visibility = View.GONE
                    }
                    auth.createUserWithEmailAndPassword(email,password).await()

                    withContext(Dispatchers.Main){
                        if(auth.currentUser != null){
                            withContext(Dispatchers.IO) {
                                auth.currentUser!!.updateProfile(profileUser).await()
                            }
                            binding.progessBar.visibility = View.GONE
                            binding.registerTitle.visibility = View.VISIBLE
                            val user = User(auth.currentUser!!.uid.toString(),auth.currentUser!!.displayName.toString(),"","","","","","")
                            val bundle = bundleOf(
                                "user" to user
                            )
                            findNavController().navigate(R.id.action_mainLoginFragment_to_genderFragment,bundle)
                        }
                    }


                }
            }
            catch (e : FirebaseAuthUserCollisionException) {
                Toast.makeText(requireContext(),"User is exist",Toast.LENGTH_LONG).show()
            }
            catch (e : FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(requireContext(),"Authentication is failed",Toast.LENGTH_LONG).show()
            }
            catch (e : FirebaseAuthException){
                Toast.makeText(requireContext(),"Authentication is failed",Toast.LENGTH_LONG).show()
            }
            catch (e : FirebaseNetworkException) {
                Toast.makeText(requireContext(),"Internet disconnected",Toast.LENGTH_LONG).show()
            }

        }
    }



}