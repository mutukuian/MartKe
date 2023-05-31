package com.example.martke.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.martke.data.Product
import com.example.martke.databinding.ProductRvItemBinding

class BestProductsAdapter:RecyclerView.Adapter<BestProductsAdapter.BestProdViewHolder>() {


    inner class BestProdViewHolder(private val binding: ProductRvItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bindData(product: Product){
            binding.apply {
                Glide.with(itemView)
                    .load(product.images[0])
                    .into(imgProduct)
              product.offerPercentage?.let {
                   val remainingPricePercentage = 1f - it
                  val priceAfterOffer = remainingPricePercentage * product.price
                  tvNewPrice.text = "Ksh ${String.format("%.2f",priceAfterOffer)}"
              }
                tvPrice.text = "Ksh ${product.price}"
                tvName.text = product.name
                tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

        }
    }

    private val differCallback = object :DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProdViewHolder {
        return BestProdViewHolder(
            ProductRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: BestProdViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bindData(product)
        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
     return differ.currentList.size
    }

    var onClick: ((Product) -> Unit)? = null
}