package com.niksatyr.randomuser.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    @GET("/")
    fun getUsers(@Query("results") count: Int)

}