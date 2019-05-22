package com.newstoday

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newstoday.Model.Article
import com.newstoday.adapter.OfflineNewsListAdapter
import com.newstoday.adapter.OfflineNewsListViewHolder
import com.newstoday.databinding.ActivityOfflineBinding
import com.newstoday.viewmodel.ArticleViewModel

class OfflineActivity : AppCompatActivity(), OfflineNewsListAdapter.OnOfflineNewsListAdapterInteractionListener {
    private var binding: ActivityOfflineBinding? = null
    private var currentItem: Article? = null
    lateinit var articleViewModel: ArticleViewModel
    lateinit var offlineNewsListAdapter: OfflineNewsListAdapter
    lateinit var offlineNewsListViewHolder: OfflineNewsListViewHolder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offline)

        articleViewModel.getSavedArticles().observe(this, Observer {
            offlineNewsListAdapter = OfflineNewsListAdapter(this, it)
            binding?.offlineListNews?.adapter = offlineNewsListAdapter
        })

        binding?.offlineListNews?.setHasFixedSize(true)
        binding?.offlineListNews?.layoutManager =
            GridLayoutManager(this, 3) as RecyclerView.LayoutManager?

//        offlineNewsListViewHolder.setOfflineArticle(articleViewModel.getSavedArticles())
    }

    override fun openNewsUrl(article: Article) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(article.url)
        startActivity(openURL)
    }

}
