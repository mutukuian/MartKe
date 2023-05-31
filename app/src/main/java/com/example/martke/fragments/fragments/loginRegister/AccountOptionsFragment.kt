package com.example.martke.fragments.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.martke.R
import com.example.martke.databinding.FragmentAccountOptionsBinding
import dagger.hilt.android.AndroidEntryPoint


class AccountOptionsFragment: Fragment(R.layout.fragment_account_options) {


    private lateinit var binding:FragmentAccountOptionsBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(
            inflater, container, false
        )

        navController = findNavController()
        onLoginClick()
//        navController = findNavController()
        onRegisterClick()

        return binding.root
    }

    private fun onRegisterClick() {
        binding.buttonRegisterAccountOptions.setOnClickListener {
            navController.navigate(R.id.action_accountOptionsFragment_to_registerFragment)
        }
    }

    private fun onLoginClick() {
        binding.apply {
            buttonLoginAccount.setOnClickListener {
                navController.navigate(R.id.action_accountOptionsFragment_to_loginFragment)
            }
        }
    }
}