package com.example.homeworkout.ui.login.SignInLayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.homeworkout.R
import com.example.homeworkout.data.local.room.model.User
import com.example.homeworkout.data.local.share.sharePreferenceUtils
import com.example.homeworkout.data.remote.model.Category
import com.example.homeworkout.databinding.FragmentSignInBinding
import com.example.homeworkout.ui.base.BaseFragment
import com.example.homeworkout.ui.login.AuthViewModel
import com.example.homeworkout.ui.main.MainScreen
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SignInFragment : BaseFragment<FragmentSignInBinding,AuthViewModel>() {

    lateinit var reference : DatabaseReference



    @Inject
    lateinit var sharePreferenceUtils : sharePreferenceUtils

    override fun layoutRes(): Int = R.layout.fragment_sign_in

    override fun viewModelClass(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun firebaseClass() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
    }



    override fun initValue() {
        reference = db.getReference("User")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
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
        if(validateEmail()) validateValue = true
        if(validatePassword()) validateValue = true
        return validateValue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
    }

    private fun addEvents() {
        binding.loginBtn.setOnClickListener {
            onSubmit()
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

    }

    private fun onSubmit() {
        if(!onValidate()){
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    binding.progessBar.visibility = View.VISIBLE
                    binding.signInTitle.visibility = View.GONE
                }
                try {
                    val email = binding.emailEditText.text.toString()
                    val pass = binding.passEditText.text.toString()
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if(it.isSuccessful){
                            getUserProfile()

                        }
                    }.await()
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

    private fun getUserProfile() {
        reference.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    for(item  in  snapshot.children){
                        if(item.key == auth.uid.toString()){
                            val id = item.child("id").value.toString()
                            val name = item.child("name").value.toString()
                            val gender = item.child("gender").toString()
                            val old = item.child("old").value.toString()
                            val height = item.child("height").value.toString()
                            val weight = item.child("weight").value.toString()
                            val goal  = item.child("goal").value.toString()
                            val level = item.child("level").value.toString()
                            val user = User(id,name,gender,old,height,weight,goal, level)
                            addDatatoLocal(user)
                            sharePreferenceUtils.saveUser(user, requireContext())
                        }
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addDatatoLocal(user : User) {
        CoroutineScope(Dispatchers.IO).launch {
            if(!viewModel.checkUser(auth.currentUser!!.uid)){
                viewModel.addUser(user)
            }
            withContext(Dispatchers.Main){
                requireActivity().startActivity(Intent(activity,MainScreen::class.java))
                requireActivity().finish()
                binding.progessBar.visibility = View.GONE
                binding.signInTitle.visibility = View.VISIBLE
            }


        }
    }

}