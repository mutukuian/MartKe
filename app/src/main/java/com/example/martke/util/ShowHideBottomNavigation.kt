package com.example.martke.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.martke.R
import com.example.martke.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(
        R.id.bottom_navigation)
    bottomNavigationView.visibility = View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(
        R.id.bottom_navigation)
    bottomNavigationView.visibility = View.VISIBLE
}