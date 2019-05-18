package com.newstoday.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.newstoday.Interface.ItemClickListener
import kotlinx.android.synthetic.main.news_layout.view.*

class ListNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener

    var article_title = itemView.article_title
    val article_image = itemView.article_image

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View) {
        itemClickListener.onClick(v, adapterPosition)
    }

}