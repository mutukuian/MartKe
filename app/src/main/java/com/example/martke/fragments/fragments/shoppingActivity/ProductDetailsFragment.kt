package com.example.martke.fragments.fragments.shoppingActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.martke.R
import com.example.martke.adapters.ColorsAdapter
import com.example.martke.adapters.SizeAdapter
import com.example.martke.adapters.ViewPager2Images
import com.example.martke.data.CartProduct
import com.example.martke.databinding.FragmentProductDetailsBinding
import com.example.martke.util.Resource
import com.example.martke.util.hideBottomNavigationView
import com.example.martke.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment: Fragment() {
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images()}
    private val sizeAdapter by lazy { SizeAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }
    private var selectedColor:Int? = null
    private var selectedSize:String? = null
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizeRecView()
        setupColorsRecView()
        setupViewPager()

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        sizeAdapter.onItemClick= {
            selectedSize = it
        }

        colorsAdapter.onItemClick = {
            selectedColor = it
        }

        binding.addToCart.setOnClickListener {
            viewModel.addUpdateProductToCart(
                CartProduct( product,1,selectedColor,selectedSize))
        }
        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        binding.addToCart.startAnimation()
                    }
                    is Resource.Success ->{
                        binding.addToCart.revertAnimation()
                        binding.addToCart.setBackgroundColor(resources.getColor(R.color.black))
                    }
                    is Resource.Error ->{
                        binding.addToCart.stopAnimation()
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text ="Ksh ${product.price}"
            tvProductDescription.text = product.description

            if (product.colors.isNullOrEmpty()){
                tvProductColor.visibility = View.INVISIBLE
            }
            if (product.sizes.isNullOrEmpty()){
                tvProductSize.visibility = View.INVISIBLE
            }
        }

        //Updating adapters

        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.let {
            colorsAdapter.differ.submitList(it)
        }
        product.sizes?.let {
            sizeAdapter.differ.submitList(it)
        }

    }

    private fun setupViewPager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }

    private fun setupColorsRecView() {
       binding.recColors.apply {
           adapter = colorsAdapter
           layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
       }
    }

    private fun setupSizeRecView() {
        binding.recSize.apply {
            adapter = sizeAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

}