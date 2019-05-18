package com.newstoday.Model

class News(
    var status: String? = null,
    var totalResults:Int = 0,
    var articles: MutableList<Article>
)