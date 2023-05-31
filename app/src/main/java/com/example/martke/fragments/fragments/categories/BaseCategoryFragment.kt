package com.example.martke.fragments.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.martke.R
import com.example.martke.adapters.BestProductsAdapter
import com.example.martke.databinding.FragmentBaseCategoryBinding
import com.example.martke.util.showBottomNavigationView

open class BaseCategoryFragment:Fragment(R.layout.fragment_base_category) {

    private lateinit var binding:FragmentBaseCategoryBinding
   protected val offerAdapter:BestProductsAdapter by lazy { BestProductsAdapter() }
    protected val bestProductsAdapter:BestProductsAdapter by lazy { BestProductsAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOfferRec()
        setupBestProductsRec()

        bestProductsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment2,b)
        }

       offerAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment2,b)
        }

        binding.recOffer.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)&& dx != 0){
                    onOfferPagingRequest()
                }
            }
        })
        binding.nestedScrollViewBaseCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY){
                onBestProductsPagingRequest()
            }
        })
    }

    fun showOfferLoading(){
        binding.offerProductsProgressBar.visibility = View.VISIBLE
    }
    fun hideOfferLoading(){
        binding.offerProductsProgressBar.visibility = View.GONE
    }
    fun showBestProductsLoading(){
        binding.bestProductsProgressBar.visibility = View.VISIBLE
    }
    fun hideBestProductsLoading(){
        binding.bestProductsProgressBar.visibility = View.GONE
    }
    private fun onBestProductsPagingRequest() {

    }

    private fun onOfferPagingRequest() {

    }


    private fun setupBestProductsRec() {

        binding.recBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter = bestProductsAdapter
        }
    }

    private fun setupOfferRec() {

        binding.recOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = offerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}