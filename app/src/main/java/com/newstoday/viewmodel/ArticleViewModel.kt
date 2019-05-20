package com.newstoday.viewmodel

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

    fun getSavedArticles(): List<Article> {
        val list = articleRepository.getSavedArticles()
        return list
    }
}