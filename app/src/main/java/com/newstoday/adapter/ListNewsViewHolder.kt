package com.newstoday.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.newstoday.common.Iso8601Parser
import com.newstoday.Model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_layout.view.*
import java.text.ParseException
import java.util.*

class ListNewsViewHolder(itemView: View,val listener: ListNewsListener?) : RecyclerView.ViewHolder(itemView) {

    var article_title = itemView.article_title
    val article_image = itemView.article_image

    fun setArticle(article: Article) {
        Picasso.get()
            .load(article.urlToImage)
            .into(article_image)
        if (article.title!!.length > 45) {
            article_title.text = article.title!!.substring(0, 45) + "..."
        } else {
            article_title.text = article.title!!
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
            listener?.setCurrentArticle(article)
        }
    }
    interface ListNewsListener{
        fun setCurrentArticle(article: Article)
    }

}
