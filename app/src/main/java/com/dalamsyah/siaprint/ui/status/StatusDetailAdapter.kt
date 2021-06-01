package com.dalamsyah.siaprint.ui.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.siaprint.databinding.LayoutItemStatusBinding
import com.dalamsyah.siaprint.databinding.LayoutItemStatusDetailBinding
import com.dalamsyah.siaprint.models.TransactionPrintD

class StatusDetailAdapter (private val listener: StatusDetailListener) :
    ListAdapter<TransactionPrintD, StatusDetailAdapter.ViewHolder>(StatusDetailDiffCallBack()) {

    class ViewHolder private constructor(val binding: LayoutItemStatusDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionPrintD, listener: StatusDetailListener){
            binding.model = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemStatusDetailBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

}

class StatusDetailListener(val clickListener: (be: TransactionPrintD) -> Unit) {
    fun onClick(be: TransactionPrintD) = clickListener(be)
}

class StatusDetailDiffCallBack : DiffUtil.ItemCallback<TransactionPrintD>(){
    override fun areItemsTheSame(oldItem: TransactionPrintD, newItem: TransactionPrintD): Boolean {
        return oldItem.created_at == newItem.created_at
    }

    override fun areContentsTheSame(
        oldItem: TransactionPrintD,
        newItem: TransactionPrintD
    ): Boolean {
        return oldItem == newItem
    }

}