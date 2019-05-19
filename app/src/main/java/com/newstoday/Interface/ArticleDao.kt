package com.newstoday.Interface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.newstoday.Model.Article

@Dao
interface ArticleDao {

    @Insert
    fun insertArticle(article: Article)

    @Update
    fun updateArticle(article: Article)

    @Query("SELECT * from Article ORDER BY publishedAt")
    fun getArticleList():List<Article>

//    @Query("SELECT * from Article WHERE id=:id")
//    fun getArticle():Article

}