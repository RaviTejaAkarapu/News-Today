package com.newstoday.Interface

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

    @Query("SELECT * from Article ORDER BY article_published_at")
    fun getArticleList():List<Article>

    @Delete
    fun deleteArticle(article: Article)

//    @Query("SELECT * from Article WHERE id=:id")
//    fun getArticle():Article

}