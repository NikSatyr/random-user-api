package com.niksatyr.randomuser.repo

import com.niksatyr.randomuser.model.User

interface UserRepository {
    suspend fun getUsers(count: Int): List<User>
}