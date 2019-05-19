package com.newstoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newstoday.Model.Article
import com.newstoday.R

class ListNewsAdapter(
    val articleList: MutableList<Article>,
    private val listener: OnListNewsAdapterInteractionListener?
) : RecyclerView.Adapter<ListNewsViewHolder>(), ListNewsViewHolder.ListNewsListener {
    override fun setCurrentArticle(article: Article) {
        listener?.setCurrentArticle(article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        val itemCount = articleList.size
        return itemCount
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        holder.setArticle(articleList[position])
    }

    interface OnListNewsAdapterInteractionListener {
        fun setCurrentArticle(article: Article)
    }


}