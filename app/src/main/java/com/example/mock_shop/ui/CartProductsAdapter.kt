package com.example.mock_shop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mock_shop.database.Product
import com.example.mock_shop.databinding.CartProductListItemBinding

class CartProductsAdapter(val clickListener: CartProductClickListener): ListAdapter<Product, CartProductsAdapter.ViewHolder>(ProductsDiffCallback()) {

    class ViewHolder private constructor(private val binding: CartProductListItemBinding): RecyclerView.ViewHolder(binding.root){

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CartProductListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Product, clickListener: CartProductClickListener){
            binding.product = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

interface CartProductClickListener {

    fun onIncrementQuantityClick(product: Product)

    fun onDecrementQuantityClick(product: Product)

    fun onDeleteClick(product: Product)

}
