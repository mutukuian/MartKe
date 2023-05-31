package com.example.martke.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.martke.R
import com.example.martke.data.Address
import com.example.martke.data.order.Order
import com.example.martke.data.order.OrderStatus
import com.example.martke.data.order.getOrderStatus
import com.example.martke.databinding.OrderItemBinding

class AllOrdersAdapter:RecyclerView.Adapter<AllOrdersAdapter.AllOrdersViewHolder>() {

    inner class AllOrdersViewHolder(val binding: OrderItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order) {
            binding.apply {
                tvOrderId.text = order.orderId.toString()
                tvOrderDate.text = order.date
                val resources = itemView.resources

                val colorDrawable = when(getOrderStatus(order.orderStatus)){
                    is OrderStatus.Ordered ->{
                        ColorDrawable(resources.getColor(R.color.g_orange_yellow))
                    }
                    is OrderStatus.Confirmed ->{
                        ColorDrawable(resources.getColor(br.com.simplepass.loadingbutton.R.color.green))
                    }
                    is OrderStatus.Delivered ->{
                        ColorDrawable(resources.getColor(br.com.simplepass.loadingbutton.R.color.green))
                    }
                    is OrderStatus.Shipped->{
                        ColorDrawable(resources.getColor(br.com.simplepass.loadingbutton.R.color.green))
                    }
                    is OrderStatus.Canceled ->{
                        ColorDrawable(resources.getColor(R.color.g_red))
                    }
                    is OrderStatus.Returned->{
                        ColorDrawable(resources.getColor(R.color.g_red))
                    }
                }
                imageOrderState.setImageDrawable(colorDrawable)
            }
        }

    }

    private val differCallback = object :DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrdersViewHolder {
      return AllOrdersViewHolder(
           OrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       )
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllOrdersViewHolder, position: Int) {
       val order = differ.currentList[position]
        holder.bind(order)
        holder.itemView.setOnClickListener {
            onClick?.invoke(order)
        }
    }

    var onClick: ((Order) -> Unit)? = null
}