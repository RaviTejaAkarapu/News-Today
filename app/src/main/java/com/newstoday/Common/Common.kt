package com.newstoday.Common

import com.newstoday.Interface.NewsService
import com.newstoday.Remote.RetrofitClient

object Common {
    val BASE_URL="https://newsapi.org/"
    val API_KEY="033106a63ff04a07b619cf06b721362c"

    val newsService:NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

}