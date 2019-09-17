package com.newstoday.di

import com.newstoday.fragment.NewsListFragment
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(newsListFragment: NewsListFragment)

}