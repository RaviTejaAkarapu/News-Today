package com.newstoday

import android.app.AlertDialog
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.Gson
import com.newstoday.Adapter.ListSourceAdapter
import com.newstoday.Common.Common
import com.newstoday.Interface.NewsService
import com.newstoday.Model.WebSite
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)

        mService = Common.newsService

        swipe_to_refresh.setOnRefreshListener {
            loadWebsiteSource(true)
        }

        recycler_view.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        dialog = SpotsDialog(this)

        loadWebsiteSource(false)

    }

    private fun loadWebsiteSource(isRefresh: Boolean) {

        if (!isRefresh) {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null") {
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                adapter = ListSourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recycler_view.adapter = adapter
            } else {
                dialog.show()
                mService.sources.enqueue(object : retrofit2.Callback<WebSite> {
                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(baseContext, response.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view.adapter = adapter

                        Paper.book().write("cache", Gson().toJson(response.body()!!))

                        dialog.dismiss()
                    }

                })
            }
        } else {
            swipe_to_refresh.isRefreshing = true

            mService.sources.enqueue(object : retrofit2.Callback<WebSite> {
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(baseContext, response.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view.adapter = adapter

                    Paper.book().write("cache", Gson().toJson(response.body()!!))

                    swipe_to_refresh.isRefreshing = false
                }

            })
        }

    }
}
