package com.dalamsyah.siaprint.ui.keranjang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.models.Company

class CompanyAdapter (private val list : MutableList<Company>, val context: Context) : RecyclerView.Adapter<CompanyAdapter.MyViewHolder>() {

    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_company, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = list[position].comp_name
    }

    override fun getItemCount(): Int = list.size
}