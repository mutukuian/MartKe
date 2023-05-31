package com.example.martke.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.martke.data.CartProduct
import com.example.martke.databinding.BillingProductsRvItemBinding
import com.example.martke.helper.getProductPrice

class BillingProductsAdapter:RecyclerView.Adapter<BillingProductsAdapter.BillingViewHolder>() {

    inner class BillingViewHolder(val binding:BillingProductsRvItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(billingProduct: CartProduct) {
            binding.apply {
                Glide.with(itemView)
                    .load(billingProduct.product.images[0])
                    .into(imageCartProduct)

                tvProductCartName.text = billingProduct.product.name
                tvBillingProductQuantity.text = billingProduct.quantity.toString()

                val priceAfterPercentage = billingProduct.product.offerPercentage.getProductPrice(billingProduct.product.price)
                tvProductCartPrice.text = "Ksh ${String.format("%.2f",priceAfterPercentage)}"

                imageCartProductColor.setImageDrawable(ColorDrawable(billingProduct.selectedColor?: Color.TRANSPARENT))
                tvCartProductSize.text = billingProduct.selectedSize?:"".also { imageCartProductSize.setImageDrawable(
                    ColorDrawable(Color.TRANSPARENT)
                ) }
            }
        }

    }

    private val differCallback = object :DiffUtil.ItemCallback<CartProduct>(){
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
         return  oldItem == newItem
        }
    }

    val differ =AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingViewHolder {

       return BillingViewHolder(
            BillingProductsRvItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BillingViewHolder, position: Int) {
       val billingProduct = differ.currentList[position]

        holder.bind(billingProduct)
    }

}