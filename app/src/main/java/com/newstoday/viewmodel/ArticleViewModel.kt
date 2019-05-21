package com.newstoday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newstoday.ApplicationController
import com.newstoday.Model.Article
import com.newstoday.repository.ArticleRepository

class ArticleViewModel : ViewModel() {
    val articleLiveData: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val articleRepository = ArticleRepository(ApplicationController.instance.getArticleDatabase())
    fun saveArticle(article: Article) {
        articleRepository.saveArticle(article)
    }

    fun unSaveArticle(article: Article) {
        articleRepository.unSaveArticle(article)
    }

    fun compareBookmarks(article: Article): Boolean{
        return articleRepository.compareBookmarks(article)
    }

    fun getSavedArticles(): LiveData<List<Article>> {
   return articleRepository.getSavedArticles()
    }
}