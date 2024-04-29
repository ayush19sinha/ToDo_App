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
import com.example.todoapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        binding.signInTextView.setOnClickListener {
            navigateToSignIn()
        }

        binding.nextBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun init() {
        navController = findNavController()
        mAuth = FirebaseAuth.getInstance()
    }

    private fun navigateToSignIn() {
        navController.navigate(R.id.action_signupFragment_to_signinFragment)
    }

    private fun registerUser() {
        val email = binding.emailEt.text.toString()
        val pass = binding.passEt.text.toString()
        val verifyPass = binding.verifyPassEt.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
            if (pass == verifyPass) {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navigateToHome()
                    } else {
                        Toast.makeText(context, task.exception?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHome() {
        navController.navigate(R.id.action_signupFragment_to_homeFragment)
    }
}
