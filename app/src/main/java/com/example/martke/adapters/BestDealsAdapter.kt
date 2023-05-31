package com.example.martke.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.martke.data.Product
import com.example.martke.databinding.BestDealsRvItemBinding

class BestDealsAdapter:RecyclerView.Adapter<BestDealsAdapter.BestDealsViewHolder>() {

   class BestDealsViewHolder(private val binding: BestDealsRvItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bindProd(product: Product){
            binding.apply {
                Glide.with(itemView)
                    .load(product.images[0])
                    .into(imgBestDeal)
                product.offerPercentage?.let {
                    val remainingPricePercentage = 1f - it
                    val priceAfterOffer = remainingPricePercentage * product.price
                    tvNewPrice.text = "Ksh ${String.format("%.2f",priceAfterOffer)}"
                }
                tvOldPrice.text = "Ksh ${product.price}"
                tvDealProductName.text = product.name
                tvOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewHolder {
       return BestDealsViewHolder(
           BestDealsRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun onBindViewHolder(holder: BestDealsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bindProd(product)
        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    var onClick: ((Product) -> Unit)? = null

}