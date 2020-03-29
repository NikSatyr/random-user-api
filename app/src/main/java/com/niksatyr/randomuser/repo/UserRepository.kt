package com.niksatyr.randomuser.repo

import com.niksatyr.randomuser.dto.User

interface UserRepository {
    suspend fun getUsers(count: Int = 20): List<User>
}