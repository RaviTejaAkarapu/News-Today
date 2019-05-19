package com.newstoday.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newstoday.Interface.ArticleDao
import com.newstoday.Model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}