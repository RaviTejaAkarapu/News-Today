package com.newstoday

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.newstoday.Adapter.ListNewsAdapter
import com.newstoday.Common.Common
import com.newstoday.Interface.NewsService
import com.newstoday.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Response

class ListNews : AppCompatActivity() {
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
        swipe_to_refresh.setOnRefreshListener { loadNews(source, true) }

        diagonalLayout.setOnClickListener {
            //yet to code
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            source = intent.getStringExtra("source")
            if (!source.isEmpty())
                loadNews(source, false)
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if (isRefreshed) {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        dialog.dismiss()

                        Picasso.get()
                            .load(response.body()!!.articles[0].urlToImage)
                            .into(top_image)

                        top_title.text = response.body()!!.articles[0].title
                        top_author.text = response.body()!!.articles[0].author

                        webHotUrl = response.body()!!.articles[0].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem, baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                })
        } else {
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

                        adapter = ListNewsAdapter(removeFirstItem, baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                })
        }

    }
}
