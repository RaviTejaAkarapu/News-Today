package com.newstoday

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.newstoday.Interface.ArticleDao
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

        offlineNewsListAdapter = OfflineNewsListAdapter(this)
        binding?.offlineListNews?.adapter = offlineNewsListAdapter
        binding?.offlineListNews?.setHasFixedSize(true)
        binding?.offlineListNews?.layoutManager = GridLayoutManager(this, 3)

        offlineNewsListViewHolder.setOfflineArticle(articleViewModel.getSavedArticles())
    }

    override fun openNewsUrl(article: Article) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(article.url)
        startActivity(openURL)
    }

}
