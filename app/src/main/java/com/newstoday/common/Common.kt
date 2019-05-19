package com.newstoday.common

import com.newstoday.Interface.NewsService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


object Common {
    val BASE_URL = "https://newsapi.org/v2/"

    val newsService: NewsService
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        return retrofit.create(NewsService::class.java)
        }
}