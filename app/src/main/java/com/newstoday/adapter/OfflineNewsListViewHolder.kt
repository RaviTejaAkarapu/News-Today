package com.newstoday.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.newstoday.Model.Article
import com.newstoday.common.Iso8601Parser
import com.newstoday.viewmodel.ArticleViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.offline_news_layout.view.*
import java.text.ParseException
import java.util.*

class OfflineNewsListViewHolder(itemView: View, val listener:OfflineListNewsListener?) : RecyclerView.ViewHolder(itemView) {
    lateinit var articleViewModel: ArticleViewModel

    var offlineArticleTitle = itemView.offline_article_title
    val offlineArticleImage = itemView.offline_article_image


    fun setOfflineArticle(articleList: List<Article>) {
        articleList.forEach { article ->
            Picasso.get()
                .load(article.urlToImage)
                .into(offlineArticleImage)

            if (article.title!!.length > 45) {
                offlineArticleTitle.text = article.title!!.substring(0, 45) + "..."
            } else {
                offlineArticleTitle.text = article.title!!
            }
            if (article.publishedAt != null) {
                var date: Date? = null
                try {
                    date = Iso8601Parser.parse(article.publishedAt!!)
                } catch (ex: ParseException) {
                    ex.printStackTrace()
                }
            }

            itemView.setOnClickListener {
                listener?.openNewsUrl(article)
            }
        }

    }
    interface OfflineListNewsListener {
        fun openNewsUrl(article: Article)
    }
}
