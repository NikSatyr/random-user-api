package com.niksatyr.randomuser.di

import com.niksatyr.randomuser.api.RandomUserApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me/"

val retrofitModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BASE_URL) }
    single { provideWebService<RandomUserApi>(get()) }
}

private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor {
        val request = it.request()
        val newUrl = request.url().newBuilder()
            .addQueryParameter("noinfo", null).build()
        it.proceed(request.newBuilder().url(newUrl).build())
    }
    .build()

private fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

private inline fun <reified T> provideWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}