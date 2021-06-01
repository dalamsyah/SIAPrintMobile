package com.dalamsyah.siaprint.ui.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.siaprint.databinding.LayoutItemStatusBinding
import com.dalamsyah.siaprint.models.TransactionPrintH

class StatusAdapter (private val listener: StatusListener) :
    ListAdapter<TransactionPrintH, StatusAdapter.ViewHolder>(StatusDiffCallBack()) {

    class ViewHolder private constructor(val binding: LayoutItemStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionPrintH, listener: StatusListener){
            binding.model = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemStatusBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StatusAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

}

class StatusListener(val clickListener: (be: TransactionPrintH) -> Unit) {
    fun onClick(be: TransactionPrintH) = clickListener(be)
}

class StatusDiffCallBack : DiffUtil.ItemCallback<TransactionPrintH>(){
    override fun areItemsTheSame(oldItem: TransactionPrintH, newItem: TransactionPrintH): Boolean {
        return oldItem.print_h_code == newItem.print_h_code
    }

    override fun areContentsTheSame(
        oldItem: TransactionPrintH,
        newItem: TransactionPrintH
    ): Boolean {
        return oldItem == newItem
    }

}