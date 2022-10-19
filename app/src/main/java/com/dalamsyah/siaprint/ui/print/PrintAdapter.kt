package com.dalamsyah.siaprint.ui.print

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.siaprint.databinding.LayoutItemPrintBinding
import com.dalamsyah.siaprint.models.Basket

class PrintAdapter (private val listener: PrintListener) :
    ListAdapter<Basket, PrintAdapter.ViewHolder>(PrintDiffCallBack()) {

    class ViewHolder private constructor(val binding: LayoutItemPrintBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Basket, listener: PrintListener){
            binding.model = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemPrintBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrintAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PrintAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

}

class PrintListener(val clickListener: (be: Basket) -> Unit) {
    fun onClick(be: Basket) = clickListener(be)
}

class PrintDiffCallBack : DiffUtil.ItemCallback<Basket>(){
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