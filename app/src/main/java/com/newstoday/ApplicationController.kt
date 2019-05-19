package com.newstoday

import android.app.Application
import androidx.room.Room
import com.newstoday.database.ArticleDatabase

class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getArticleDatabase(): ArticleDatabase {
        return Room.databaseBuilder(this, ArticleDatabase::class.java, "activity_databse").build()
    }


    companion object {
        public lateinit var instance: ApplicationController
    }
}