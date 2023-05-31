package com.example.martke.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

import com.example.martke.R
import com.example.martke.databinding.ActivityShoppingBinding
import com.example.martke.util.Resource
import com.example.martke.viewmodel.CartViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var navController: NavController

    val viewModel by viewModels<CartViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHFragment = supportFragmentManager.findFragmentById(R.id.shopping_nav_host) as NavHostFragment
        navController = navHFragment.navController
        onNavigationCont()

        lifecycleScope.launchWhenStarted {
            viewModel.cartProducts.collectLatest {
                when(it){
                    is Resource.Success ->{
                        val count = it.data?.size ?:0
                        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
                        bottomNavigation.getOrCreateBadge(R.id.cartFragment).apply {
                            number = count
                            backgroundColor = resources.getColor(R.color.g_blue)
                        }
                    }else ->Unit
                }
            }
        }
        }
    private fun onNavigationCont() {
        binding.bottomNavigation.setupWithNavController(navController)
    }


}