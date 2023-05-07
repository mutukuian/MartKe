package com.example.martke.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.martke.R
import com.example.martke.databinding.FragmentIntroductionBinding
import dagger.hilt.android.AndroidEntryPoint

class IntroductionFragment: Fragment(R.layout.fragment_introduction) {

    private lateinit var binding: FragmentIntroductionBinding
    private lateinit var navController: NavController


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
            navController.navigate(R.id.registerFragment)
        }

    }
}
