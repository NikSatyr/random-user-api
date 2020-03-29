package com.niksatyr.randomuser.api

import com.niksatyr.randomuser.dto.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("/")
    fun getUsers(@Query("results") count: Int): Response<List<User>>

}