package com.newstoday.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newstoday.Common.Iso8601Parser
import com.newstoday.Interface.ItemClickListener
import com.newstoday.Model.Article
import com.newstoday.R
import com.squareup.picasso.Picasso
import java.sql.Date
import java.text.ParseException

class ListNewsAdapter(val articleList: MutableList<Article>, private val context: Context) :
    RecyclerView.Adapter<ListNewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        val itemCount = articleList.size
        return itemCount
    }

    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
        Picasso.get()
            .load(articleList[position].urlToImage)
            .into(holder.article_image)
        if(articleList[position].title!!.length>65){
            holder.article_title.text = articleList[position].title!!.substring(0,65)+"..."
        }
        else{
            holder.article_title.text = articleList[position].title!!
        }
        if(articleList[position].publishedAt!=null)
        {
            var date: Date?=null
            try{
                date=Iso8601Parser.parse(articleList[position].publishedAt!!)
            }catch (ex:ParseException)
            {
                ex.printStackTrace()
            }
        }


        holder.setItemClickListener(object :ItemClickListener{
            override fun onClick(view: View, position: Int) {

            }
        })
    }


}