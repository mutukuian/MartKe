package com.example.martke.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.martke.databinding.ColorRecItemBinding

class ColorsAdapter:RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {

    private var selectedPosition = -1

    inner class ColorsViewHolder(val binding: ColorRecItemBinding):RecyclerView.ViewHolder(binding.root){

       fun bind(color:Int,position: Int){
           val imageDrawable = ColorDrawable(color)
           binding.imageColor.setImageDrawable(imageDrawable)
           if (position == selectedPosition){
               //color iss selected
               binding.apply {
                   imageShadow.visibility = View.VISIBLE
                   imagePicked.visibility = View.VISIBLE
               }
           }else{
               //color not selected
               binding.apply {
                   imageShadow.visibility = View.INVISIBLE
                   imagePicked.visibility = View.INVISIBLE
               }

           }
       }
    }

    private val differCallback = object :DiffUtil.ItemCallback<Int>(){
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    val  differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
      return ColorsViewHolder(
          ColorRecItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      )
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color,position)

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)//unselects selected item
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)

            onItemClick?.invoke(color)
        }
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }

    var onItemClick:((Int) -> Unit)? = null
}