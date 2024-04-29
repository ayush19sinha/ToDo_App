package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        binding.textViewSignUp.setOnClickListener {
            navigateToSignUp()
        }

        binding.nextBtn.setOnClickListener {
            loginUser()
        }
    }

    private fun init() {
        navController = findNavController()
        mAuth = FirebaseAuth.getInstance()
    }

    private fun navigateToSignUp() {
        navController.navigate(R.id.action_signinFragment_to_signupFragment)
    }

    private fun loginUser() {
        val email = binding.emailEt.text.toString()
        val pass = binding.passEt.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToHome()
                } else {
                    Toast.makeText(context, task.exception?.message ?: "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHome() {
        navController.navigate(R.id.action_signinFragment_to_homeFragment)
    }
}
