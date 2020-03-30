package com.niksatyr.randomuser.repo

import com.niksatyr.randomuser.api.RandomUserApi
import com.niksatyr.randomuser.dto.User

class RemoteUserRepository(private val userApi: RandomUserApi) : UserRepository {

    override suspend fun getUsers(count: Int): List<User> {
        if (count < 1) {
            throw IllegalArgumentException("Cannot send request for less than 1 user")
        }
        val response = userApi.getUsers(count)
        if (response.isSuccessful) {
            return response.body()?.users ?: throw IllegalStateException("No users were fetched")
        } else {
            throw IllegalStateException("An error occurred while executing query")
        }
    }

}