package com.example.martke.fragments.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.martke.R
import com.example.martke.data.User
import com.example.martke.databinding.FragmentRegisterBinding
import com.example.martke.util.RegisterValidation
import com.example.martke.util.Resource
import com.example.martke.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment: Fragment(R.layout.fragment_register) {

    private val TAG = "RegisterFragment"

  private lateinit var  binding:FragmentRegisterBinding
  private val viewModel: RegisterViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)

        navController = findNavController()
        onLogTextViewClick()

        return binding.root
    }

    private fun onLogTextViewClick() {
        binding.tvDontHaveAccount.setOnClickListener{
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            buttonRegisterRegister.setOnClickListener {
                val user = User(
                    firstName = firstName.text.toString().trim(),
                    lastName = lastName.text.toString().trim(),
                    email = email.text.toString().trim()
                )
                val password = password.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.buttonRegisterRegister.startAnimation()
                    }
                    is Resource.Success -> {
                        Log.d("test", it.data.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    is Resource.Error -> {
                        Log.e(TAG, it.message.toString())
                        binding.buttonRegisterRegister.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.email.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if (validation.password is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.password.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }
    }
}