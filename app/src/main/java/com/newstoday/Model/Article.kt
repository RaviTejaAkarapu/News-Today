package com.newstoday.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="article_id")
    var id: Int? = null,

    @ColumnInfo(name = "article_author")
    var author: String? = null,

    @ColumnInfo(name = "article_title")
    var title: String? = null,

    @ColumnInfo(name = "article_description")
    var description: String? = null,

    @ColumnInfo(name = "article_url")
    var url: String? = null,

    @ColumnInfo(name = "article_image_url")
    var urlToImage: String? = null,

    @ColumnInfo(name = "article_published_at")
    var publishedAt: String? = null,

    @ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean = false
)