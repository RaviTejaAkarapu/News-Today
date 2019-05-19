package com.newstoday.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.newstoday.Interface.NewsService
import com.newstoday.Model.Article
import com.newstoday.Model.News
import com.newstoday.R
import com.newstoday.adapter.ListNewsAdapter
import com.newstoday.common.Common
import com.newstoday.databinding.FragmentChannelBinding
import com.newstoday.viewmodel.ArticleViewModel
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Response

class PlaceholderFragment : androidx.fragment.app.Fragment(), ListNewsAdapter.OnListNewsAdapterInteractionListener {
    val TAG: String = "LIST NEWS TAGGG"
    var source = ""
    var webHotUrl: String? = ""
    lateinit var dialog: SpotsDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter
    lateinit var articleViewModel: ArticleViewModel
    private var articles: ArrayList<Article> = arrayListOf()
    private var category: String? = ""
    private var binding: FragmentChannelBinding? = null
    private var currentItem: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        category = arguments?.getString(ARG_CATEGORY)
        loadNews(category!!, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel, container, false)
        articleViewModel.articleLiveData.observe(this, Observer {

            if (it != null) {
                if (currentItem == null) {
                    currentItem = it[0]
                }
                setCurrentArticle(currentItem!!)
                updateList(it)
            }
        })

        binding?.fragmentSwipeToRefresh?.setOnRefreshListener {
            loadNews(category!!, true)
        }
        binding?.listNews?.setHasFixedSize(true)
        binding?.listNews?.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        return binding?.root
    }


    override fun setCurrentArticle(article: Article) {
        Picasso.get()
            .load(article.urlToImage)
            .into(binding?.topImage)

        binding?.topTitle?.text = article.title
//        binding?.topAuthor?.text = article.author
        currentItem = article

    }


    private fun loadNews(category: String, isRefreshed: Boolean) {
        if (!isRefreshed) {
            mService = Common.newsService
            dialog = SpotsDialog(context)
            dialog.show()
            mService.getNewsFromCategory(category)
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        dialog.dismiss()

                        setCurrentArticle(response.body()!!.articles[0])

                        webHotUrl = response.body()!!.articles[0].url

                        articleViewModel.articleLiveData.value = response.body()!!.articles
//                        val removeFirstItem = response.body()!!.articles
//                        removeFirstItem.removeAt(0)
//                        articles.addAll(removeFirstItem)
//                        updateList(removeFirstItem)
                    }

                })
        } else {
            binding?.fragmentSwipeToRefresh?.isRefreshing = true

            mService.getNewsFromCategory(category)
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        binding?.fragmentSwipeToRefresh?.isRefreshing = false

                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        binding?.fragmentSwipeToRefresh?.isRefreshing = false
                        Picasso.get()
                            .load(response.body()!!.articles[0].urlToImage)
                            .into(binding?.topImage)

                        binding?.topTitle?.text = response.body()!!.articles[0].title
//                        binding?.topAuthor?.text = response.body()!!.articles[0].author

                        webHotUrl = response.body()!!.articles[0].url

//                        val removeFirstItem = response.body()!!.articles
//                        removeFirstItem.removeAt(0)
//
//                        updateList(removeFirstItem)
                    }

                })
        }
    }

    private fun updateList(articleList: MutableList<Article>) {
        adapter = ListNewsAdapter(articleList, this@PlaceholderFragment)
        adapter.notifyDataSetChanged()
        binding?.listNews?.adapter = adapter
    }

    companion object {
        private val ARG_CATEGORY = "CATEGORY"
        fun newInstance(category: String): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }
}