package com.example.martke.fragments.fragments.shoppingActivity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.martke.R
import com.example.martke.adapters.HomeViewPagerAdapter
import com.example.martke.databinding.FragmentHomeBinding
import com.example.martke.fragments.fragments.categories.*

import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding:FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val categoriesFragments = arrayListOf<Fragment>(
                MainCategoryFragment(),
                LaptopFragment(),
                PhoneFragment(),
                SoundSystemFragment(),
                AccessoryFragment(),
                WifiFragment()
            )

            binding.viewpagerHome.isUserInputEnabled = false

            val viewPager2Adapter = HomeViewPagerAdapter(categoriesFragments,childFragmentManager,lifecycle)
            binding.viewpagerHome.adapter = viewPager2Adapter
            TabLayoutMediator(binding.tabLayout,binding.viewpagerHome){
                tab ,position ->
                run {
                    when (position) {
                        0 -> tab.text = "Home"
                        1 -> tab.text = "Laptop"
                        2 -> tab.text = "Phone"
                        3 -> tab.text = "SoundSystem"
                        4 -> tab.text = "Accessory"
                        5 -> tab.text = "Wifi"

                    }
                }
            }.attach()
        }


    }




