package com.example.todoapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        val isLoggedIn = isUserLoggedIn()
        Handler(Looper.myLooper()!!).postDelayed({
            navigateToDestination(isLoggedIn)
        }, 2000)
    }

    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
    }

    private fun isUserLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }

    private fun navigateToDestination(isLoggedIn: Boolean) {
        val destinationId = if (isLoggedIn) R.id.action_splashFragment_to_homeFragment
        else R.id.action_splashFragment_to_signinFragment
        navController.navigate(destinationId)
    }
}
