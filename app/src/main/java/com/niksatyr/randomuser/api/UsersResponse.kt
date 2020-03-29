package com.niksatyr.randomuser.api

import com.google.gson.annotations.SerializedName
import com.niksatyr.randomuser.dto.User

data class UsersResponse(@SerializedName("results") val users: List<User>)