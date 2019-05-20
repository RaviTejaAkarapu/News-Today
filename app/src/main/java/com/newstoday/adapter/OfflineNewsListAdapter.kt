package com.newstoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newstoday.Interface.ArticleDao
import com.newstoday.Model.Article
import com.newstoday.R
import com.newstoday.viewmodel.ArticleViewModel

class OfflineNewsListAdapter(
    private val listener: OnOfflineNewsListAdapterInteractionListener?
) :
    RecyclerView.Adapter<OfflineNewsListViewHolder>(),
    OfflineNewsListViewHolder.OfflineListNewsListener {
    val articleList=ArticleViewModel().getSavedArticles()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineNewsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.offline_news_layout, parent, false)
        return OfflineNewsListViewHolder(itemView, this)

    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: OfflineNewsListViewHolder, position: Int) {
        holder.setOfflineArticle(articleList)
    }

    override fun openNewsUrl(article: Article) {
        listener?.openNewsUrl(article)
    }


    interface OnOfflineNewsListAdapterInteractionListener {
        fun openNewsUrl(article: Article)
    }
}