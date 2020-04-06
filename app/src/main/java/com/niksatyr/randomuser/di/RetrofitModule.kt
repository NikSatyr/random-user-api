package com.niksatyr.randomuser.di

import com.niksatyr.randomuser.api.RandomUserApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me/"

val retrofitModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newUrl = request.url().newBuilder()
                    .addQueryParameter("noinfo", null).build()
                it.proceed(request.newBuilder().url(newUrl).build())
            }
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(RandomUserApi::class.java)
    }
}
