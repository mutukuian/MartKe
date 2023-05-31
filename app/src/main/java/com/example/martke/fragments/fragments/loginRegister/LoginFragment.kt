package com.example.martke.fragments.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.martke.R
import com.example.martke.activities.ShoppingActivity
import com.example.martke.databinding.FragmentLoginBinding
import com.example.martke.dialog.setupBottomSheetDialog
import com.example.martke.util.Resource
import com.example.martke.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
   private  val viewModel:LoginViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater,container,false)

        navController = findNavController()
        onRegisterTextViewClick()

        return binding.root
    }

    private fun onRegisterTextViewClick() {
        binding.tvDontHaveAccountRegister.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonLoginlogin.setOnClickListener {
                val email = email.text.toString().trim()
                val password = password.text.toString()

                viewModel.login(email, password)
            }
        }

        binding.tvforgotPassword.setOnClickListener {
            setupBottomSheetDialog { email->

                viewModel.resetPassword(email)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when(it){
                    is Resource.Loading ->{

                    }
                    is Resource.Success ->{

                        Snackbar.make(requireView(),"Reset link was sent to your email",Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error ->{
                        Snackbar.make(requireView(),"Error:${it.message}",Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect{
                when(it){
                    is Resource.Loading ->{

                        binding.buttonLoginlogin.startAnimation()
                    }
                    is Resource.Success ->{
                        binding.buttonLoginlogin.revertAnimation()
                        Intent(requireActivity(),ShoppingActivity::class.java).also {
                            intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }

                    }
                    is Resource.Error ->{

                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                        binding.buttonLoginlogin.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
    }
}