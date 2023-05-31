package com.example.martke.fragments.fragments.loginRegister


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.martke.R
import com.example.martke.activities.ShoppingActivity
import com.example.martke.databinding.FragmentIntroductionBinding
import com.example.martke.viewmodel.IntroductionViewModel
import com.example.martke.viewmodel.IntroductionViewModel.Companion.ACCOUNT_OPTIONS_FRAGMENT
import com.example.martke.viewmodel.IntroductionViewModel.Companion.SHOPPING_ACTIVITY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionFragment: Fragment(R.layout.fragment_introduction) {

    private lateinit var binding: FragmentIntroductionBinding
    private lateinit var navController: NavController

    private val viewModel:IntroductionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(inflater,container
        ,false)

        navController = findNavController()
        onIntroClick()


        return binding.root
    }

    fun onIntroClick(){
        binding.startBtn.setOnClickListener {
            viewModel.startButtonClicked()
            navController.navigate(R.id.accountOptionsFragment)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.navigate.collect{
                when(it){
                    SHOPPING_ACTIVITY ->{
                        Intent(requireActivity(), ShoppingActivity::class.java).also {
                                intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                    }
                }

                    ACCOUNT_OPTIONS_FRAGMENT ->{
                        navController.navigate(it)
                    }
                    else -> Unit
                }
            }
        }
    }
}
