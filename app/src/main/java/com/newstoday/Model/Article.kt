package com.newstoday.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? =null,

    @ColumnInfo
    var author: String? = null,

    @ColumnInfo
    var title: String? = null,

    @ColumnInfo
    var description: String? = null,

    @ColumnInfo
    var url: String? = null,

    @ColumnInfo
    var urlToImage: String? = null,

    @ColumnInfo
    var publishedAt: String? = null
)