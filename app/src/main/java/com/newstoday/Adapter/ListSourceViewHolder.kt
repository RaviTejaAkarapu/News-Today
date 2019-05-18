package com.newstoday.Adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.newstoday.Interface.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*

class ListSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var ItemClickListener: ItemClickListener

    var source_title = itemView.source_news_name
    init{
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.ItemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        ItemClickListener.onClick(v!!, adapterPosition)
        Log.d("ViewHolder","stay tuned to the next episode === $adapterPosition")

    }

}