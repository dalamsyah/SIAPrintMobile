package com.dalamsyah.siaprint.ui.keranjang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.siaprint.databinding.LayoutItemKeranjangBinding
import com.dalamsyah.siaprint.models.Basket

class KeranjangAdapter (private val listener: KeranjangListener) :
    ListAdapter<Basket, KeranjangAdapter.ViewHolder>(KeranjangDiffCallBack()) {

    class ViewHolder private constructor(val binding: LayoutItemKeranjangBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Basket, listener: KeranjangListener){
            binding.model = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemKeranjangBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: KeranjangAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

}

class KeranjangListener(val clickListener: (be: Basket) -> Unit) {
    fun onClick(be: Basket) = clickListener(be)
}

class KeranjangDiffCallBack : DiffUtil.ItemCallback<Basket>(){
    override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Basket,
        newItem: Basket
    ): Boolean {
        return oldItem == newItem
    }

}