package com.newstoday.Interface

import androidx.lifecycle.LiveData
import androidx.room.*
import com.newstoday.Model.Article

@Dao
interface ArticleDao {

    @Insert
    fun insertArticle(article: Article)

    @Update
    fun updateArticle(article: Article)

    @Query("SELECT COUNT(*) FROM Article")
    fun getOfflineArticleCount():Int

    @Query("SELECT * from Article GROUP BY article_title")
    fun getArticleList(): LiveData<List<Article>>

    @Query("delete from Article where article_title=:article_title")
    fun deleteArticle(article_title: String)

    @Query("select count(*) from Article where article_title=:article_title")
    fun compareBookmarks(article_title: String): Int

//    @Query("SELECT * from Article WHERE id=:id")
//    fun getArticle():Article

}