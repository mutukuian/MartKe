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

import com.example.martke.databinding.CartProductItemBinding

import com.example.martke.helper.getProductPrice

class CartProductAdapter : RecyclerView.Adapter<CartProductAdapter.CartProductsViewHolder>() {

    inner class CartProductsViewHolder(val binding:CartProductItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bindData(cartProduct: CartProduct){
            binding.apply {
                Glide.with(itemView)
                    .load(cartProduct.product.images[0])
                    .into(imageCartProduct)
             tvProductCartName.text = cartProduct.product.name
                tvCartProductQuantity.text = cartProduct.quantity.toString()
                val priceAfterPercentage = cartProduct.product.offerPercentage.getProductPrice(cartProduct.product.price)
                tvProductCartPrice.text = "Ksh ${String.format("%.2f",priceAfterPercentage)}"

                imageCartProductColor.setImageDrawable(ColorDrawable(cartProduct.selectedColor?: Color.TRANSPARENT))
                tvCartProductSize.text = cartProduct.selectedSize?:"".also { imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT)) }
            }
        }


    }
    //setup the diffutil
    private  val  differCallback = object : DiffUtil.ItemCallback<CartProduct>(){
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product.id == newItem.product.id
        }
        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductsViewHolder {
        return CartProductsViewHolder(
            CartProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CartProductsViewHolder, position: Int) {
        val cartproduct = differ.currentList[position]
        holder.bindData( cartproduct)
        holder.itemView.setOnClickListener {
            onProdClick?.invoke(cartproduct)
        }
        holder.binding.imagePlus.setOnClickListener {
            onPlusClick?.invoke(cartproduct)
        }
        holder.binding.imageMinus.setOnClickListener {
            onMinusClick?.invoke(cartproduct)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onProdClick: ((CartProduct) -> Unit)? = null
    var onPlusClick: ((CartProduct) -> Unit)? = null
    var onMinusClick: ((CartProduct) -> Unit)? = null

}
