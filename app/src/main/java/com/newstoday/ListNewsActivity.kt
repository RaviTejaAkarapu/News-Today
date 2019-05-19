package com.newstoday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.newstoday.adapter.ListNewsAdapter
import com.newstoday.common.Common
import com.newstoday.Interface.NewsService
import com.newstoday.Model.Article
import com.newstoday.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Response

class ListNewsActivity : AppCompatActivity(), ListNewsAdapter.OnListNewsAdapterInteractionListener {
    val TAG: String = "LIST NEWS TAGGG"
    var source = ""
    var webHotUrl: String? = ""
    lateinit var dialog: SpotsDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService
        dialog = SpotsDialog(this)
        swipe_to_refresh.setOnRefreshListener { loadNews(source, false) }
        if (intent != null) {
            source = intent.getStringExtra("source")
            if (!source.isEmpty())
                loadNews(source, false)
        }
        list_news.setHasFixedSize(true)
        list_news.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

    }

    override fun setCurrentArticle(article: Article) {
        Picasso.get()
            .load(article.urlToImage)
            .into(top_image)

        top_title.text = article.title
        top_author.text = article.author

    }

    private fun loadNews(source: String?, isRefreshed: Boolean, index: Int = 0) {
        if (!isRefreshed) {
            dialog.show()
            mService.getNewsFromCategory("business")
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        dialog.dismiss()

                        setCurrentArticle(response.body()!!.articles[index])

                        webHotUrl = response.body()!!.articles[index].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem.removeAt(index)

                        adapter = ListNewsAdapter(removeFirstItem, this@ListNewsActivity)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                })
        } else {
            swipe_to_refresh.isRefreshing = true

            mService.getNewsFromCategory("business")
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        swipe_to_refresh.isRefreshing = false

                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipe_to_refresh.isRefreshing = false

                        Picasso.get()
                            .load(response.body()!!.articles[0].urlToImage)
                            .into(top_image)

                        top_title.text = response.body()!!.articles[0].title
                        top_author.text = response.body()!!.articles[0].author

                        webHotUrl = response.body()!!.articles[0].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem, this@ListNewsActivity)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                })
        }

    }
}
