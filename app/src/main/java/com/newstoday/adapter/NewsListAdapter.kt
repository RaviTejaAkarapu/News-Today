package com.newstoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newstoday.Model.Article
import com.newstoday.R

class NewsListAdapter(
    val articleList: MutableList<Article>,
    private val listener: OnNewsListAdapterInteractionListener?
) : RecyclerView.Adapter<NewsListViewHolder>(), NewsListViewHolder.ListNewsListener {
    override fun setCurrentArticle(article: Article) {
        listener?.setCurrentArticle(article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)
        return NewsListViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        val itemCount = articleList.size
        return itemCount
    }

    override fun onBindViewHolder(newsListViewHolder: NewsListViewHolder, position: Int) {
        newsListViewHolder.setArticle(articleList[position])
    }

    override fun openNewsUrl(article: Article) {
        listener?.openNewsUrl(article)
    }

    interface OnNewsListAdapterInteractionListener {
        fun setCurrentArticle(article: Article)

        fun openNewsUrl(article: Article)
    }


}