package com.newstoday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newstoday.Model.Article
import com.newstoday.R
import com.newstoday.viewmodel.ArticleViewModel

class OfflineNewsListAdapter(
    private val listener: OnOfflineNewsListAdapterInteractionListener?,
private val articleList:List<Article>
) :
    RecyclerView.Adapter<OfflineNewsListViewHolder>(),
    OfflineNewsListViewHolder.OfflineListNewsListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineNewsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.offline_news_layout, parent, false)
        return OfflineNewsListViewHolder(itemView, this)

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: OfflineNewsListViewHolder, position: Int) {
        holder.setOfflineArticle(articleList[position])
    }

    override fun openNewsUrl(article: Article) {
        listener?.openNewsUrl(article)
    }


    interface OnOfflineNewsListAdapterInteractionListener {
        fun openNewsUrl(article: Article)
    }
}