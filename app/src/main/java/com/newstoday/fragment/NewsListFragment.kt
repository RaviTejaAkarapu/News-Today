package com.newstoday.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.newstoday.Interface.NewsService
import com.newstoday.LoginActivity
import com.newstoday.Model.Article
import com.newstoday.Model.News
import com.newstoday.R
import com.newstoday.adapter.NewsListAdapter
import com.newstoday.databinding.FragmentChannelBinding
import com.newstoday.di.DaggerApiComponent
import com.newstoday.viewmodel.ArticleViewModel
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class NewsListFragment : Fragment(), NewsListAdapter.OnNewsListAdapterInteractionListener {

    val TAG: String = "LIST NEWS TAG"
    var source = ""
    var webHotUrl: String? = ""
    lateinit var dialog: SpotsDialog
    lateinit var newsListAdapter: NewsListAdapter
    lateinit var articleViewModel: ArticleViewModel
    private var articles: ArrayList<Article> = arrayListOf()
    private var category: String? = ""
    private var binding: FragmentChannelBinding? = null
    private var currentItem: Article? = null

    @Inject
    lateinit var mService: NewsService

    init {
        DaggerApiComponent.create().inject(this)
    }

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
                setBookmark(currentItem!!)
            }
        })

        binding?.topImage?.setOnClickListener {
            openNewsUrl(currentItem!!)
        }

        binding?.bookmarkBorder?.setOnClickListener {
            Log.d("NewsListFragment", "USERID=${FirebaseAuth.getInstance().uid}")
            if (FirebaseAuth.getInstance().uid != null) {
                if (currentItem!!.isBookmarked) {
                    articleViewModel.unSaveArticle(currentItem!!)
                } else {
                    articleViewModel.saveArticle(currentItem!!)
                }
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }

//        binding?.bookmarkFill?.setOnClickListener {
//            if (currentItem?.isBookmarked!!) {
//                currentItem?.isBookmarked=false
//                it.visibility = View.GONE
//                binding?.bookmarkBorder?.visibility = View.VISIBLE
//                articleViewModel.unSaveArticle(currentItem!!)
//                Toast.makeText(context,"Article removed from DOWNLOADS",Toast.LENGTH_SHORT).show()
//            }
//        }

        binding?.fragmentSwipeToRefresh?.setOnRefreshListener {
            loadNews(category!!, true)
        }
        binding?.listNews?.setHasFixedSize(true)
        binding?.listNews?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return binding?.root
    }

    private fun setBookmark(currentItem: Article) {

        articleViewModel.compareBookmarks(currentItem).observe(this, Observer {

            if (it) {
                binding?.bookmarkBorder?.setImageResource(R.drawable.ic_bookmark_fill)
                currentItem.isBookmarked = true
//                Toast.makeText(context,"Article saved to DOWNLOADS",Toast.LENGTH_SHORT).show()
            } else {
                binding?.bookmarkBorder?.setImageResource(R.drawable.ic_bookmark_border)
                currentItem.isBookmarked = false
//                Toast.makeText(context,"Article removed from DOWNLOADS",Toast.LENGTH_SHORT).show()
            }
        })

//        if (articleViewModel.compareBookmarks(currentItem)) {
//            binding?.bookmarkBorder?.setImageResource(R.drawable.ic_bookmark_fill)
//            currentItem.isBookmarked = true
//            articleViewModel.saveArticle(currentItem)
//            Toast.makeText(context,"Article saved to DOWNLOADS",Toast.LENGTH_SHORT).show()
//        } else {
//            binding?.bookmarkBorder?.setImageResource(R.drawable.ic_bookmark_border)
//            currentItem.isBookmarked = false
//            articleViewModel.unSaveArticle(currentItem)
//            Toast.makeText(context,"Article removed from DOWNLOADS",Toast.LENGTH_SHORT).show()
//        }
    }

    override fun openNewsUrl(article: Article) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(article.url)
        startActivity(openURL)
    }

    override fun setCurrentArticle(article: Article) {
        Picasso.get()
            .load(article.urlToImage)
            .into(binding?.topImage)
        binding?.topTitle?.text = article.title
//        binding?.topAuthor?.text = article.author
        currentItem = article
        setBookmark(currentItem!!)
    }

    private fun loadNews(category: String, isRefreshed: Boolean) {
        if (!isRefreshed) {
            dialog = SpotsDialog(context)
            dialog.show()
            mService.getNewsFromCategory(category)
                .enqueue(object : retrofit2.Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {}
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
//                        updateList(removeFirstItem)
                    }
                })
        }
    }

    private fun updateList(articleList: MutableList<Article>) {
        newsListAdapter = NewsListAdapter(articleList, this@NewsListFragment)
        newsListAdapter.notifyDataSetChanged()
        binding?.listNews?.adapter = newsListAdapter
    }

    companion object {
        private val ARG_CATEGORY = "CATEGORY"
        fun newInstance(category: String): NewsListFragment {
            val fragment = NewsListFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }
}