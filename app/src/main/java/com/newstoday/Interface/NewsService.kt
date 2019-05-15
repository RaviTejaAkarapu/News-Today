package com.newstoday.Interface

import com.newstoday.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @get:GET("v2/sources?apiKey=033106a63ff04a07b619cf06b721362c")
    val sources: Call<WebSite>
}