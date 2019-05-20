package com.newstoday.Interface

import com.newstoday.Model.News
import retrofit2.Call
import retrofit2.http.*

interface NewsService {
    @GET("top-headlines")
    fun getNewsFromCategory(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String="033106a63ff04a07b619cf06b721362c",
        @Query("country") country: String="in"
    ): Call<News>
}