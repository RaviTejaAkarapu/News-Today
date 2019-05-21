package com.newstoday.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.newstoday.Interface.ArticleDao
import com.newstoday.Model.Article
import com.newstoday.database.ArticleDatabase

class ArticleRepository(
    private var articleDatabase: ArticleDatabase
) {

    fun saveArticle(article: Article) {
        InsertAsyncTask(articleDatabase.articleDao()).execute(article)
    }

    fun unSaveArticle(article: Article) {
        DeleteAsyncTask(articleDatabase.articleDao()).execute(article)
    }

    fun getSavedArticles(): LiveData<List<Article>> {
        val list = articleDatabase.articleDao().getArticleList()
        return list
    }

    fun compareBookmarks(article: Article): Boolean {
//        return (articleDatabase.articleDao().compareBookmarks(article.title!!) != 0)

//        Log.d(
//            "ArticleRepository",
//            "compare bookmarks return value ${articleDatabase.articleDao().compareBookmarks(article.title!!)}"
//        )
        return false
    }

    companion object {
        class InsertAsyncTask(private val articleDao: ArticleDao) : AsyncTask<Article, Any?, Any?>() {
            override fun doInBackground(vararg params: Article?) {
                val article = params[0]
                articleDao.insertArticle(article!!)
            }
        }

        class DeleteAsyncTask(private val articleDao: ArticleDao) : AsyncTask<Article, Any?, Any?>() {
            override fun doInBackground(vararg params: Article?) {
                val article = params[0]
                articleDao.deleteArticle(article?.title!!)
            }
        }

//        class CompareAsyncTask(private val articleDao: ArticleDao) : AsyncTask<Article, Any?, Any?>() {
//            override fun doInBackground(vararg params: Article?) {
//                val article = params[0]
//                var bookmarks:Int = 0
//                bookmarks = articleDao.compareBookmarks(article?.title!!)
//
//                fun bookmarks(): Int {
//                   return bookmarks
//                }
//            }
//        }
    }
}