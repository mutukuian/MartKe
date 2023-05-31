package com.example.martke.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.martke.databinding.ViewpagerImageItemBinding


class ViewPager2Images:RecyclerView.Adapter<ViewPager2Images.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ViewpagerImageItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(imagePath: String){
            Glide.with(itemView)
                .load(imagePath)
                .into(binding.imageProductDetails)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        return ViewPagerViewHolder(
           ViewpagerImageItemBinding.inflate(
               LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    private val differCallback = object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
}