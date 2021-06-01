package com.dalamsyah.siaprint.ui.upload

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.siaprint.databinding.LayoutItemUploadBinding
import com.dalamsyah.siaprint.models.Upload

class UploadAdapter (private val listener: UploadListener) :
    ListAdapter<Upload, UploadAdapter.ViewHolder>(UploadDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

    class ViewHolder private constructor(val binding: LayoutItemUploadBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Upload, listener: UploadListener){
            binding.model = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemUploadBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun submitList(list: MutableList<Upload>?) {
        super.submitList(list)
    }
}

class UploadListener(val clickListener: (be: Upload) -> Unit) {
    fun onClick(be: Upload) = clickListener(be)
}

class UploadDiffCallBack : DiffUtil.ItemCallback<Upload>() {
    override fun areItemsTheSame(oldItem: Upload, newItem: Upload): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Upload, newItem: Upload): Boolean {
        return oldItem == newItem
    }

}