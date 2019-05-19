package com.newstoday.repository

import androidx.room.Room
import android.os.AsyncTask
import com.newstoday.Interface.ArticleDao
import com.newstoday.Model.Article
import com.newstoday.database.ArticleDatabase

class ArticleRepository (
    private var articleDatabase:ArticleDatabase
){

    fun saveArticle(article:Article){
        InsertAsyncTask(articleDatabase.articleDao()).execute(article)
    }

    companion object{
        class InsertAsyncTask(private val articleDao: ArticleDao): AsyncTask<Article,Any?,Any?>(){
            override fun doInBackground(vararg params: Article?) {
                val article = params[0]
                articleDao.insertArticle(article!!)
            }

        }
    }

}