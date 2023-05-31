package com.example.martke.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.martke.databinding.SizeRecItemBinding



class SizeAdapter:RecyclerView.Adapter<SizeAdapter.SizeViewHolder>(){

    private var selectedPosition = -1

    inner class SizeViewHolder(val binding: SizeRecItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(size:String,position: Int){
          binding.tvSize.text = size
            if (position == selectedPosition){
                //size is selected
                binding.apply {
                    imageShadow.visibility = View.VISIBLE
                }
            }else{
                //size not selected
                binding.apply {
                    imageShadow.visibility = View.INVISIBLE
                }

            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val  differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  SizeViewHolder{
        return SizeViewHolder(
            SizeRecItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size, position)

        holder.itemView.setOnClickListener {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition)//unselects selected item
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)

            onItemClick?.invoke(size)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick:((String) -> Unit)? = null
}