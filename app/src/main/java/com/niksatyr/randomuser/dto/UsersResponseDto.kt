package com.niksatyr.randomuser.api

import com.google.gson.annotations.SerializedName
import com.niksatyr.randomuser.dto.UserDto

data class UsersResponse(@SerializedName("results") val users: List<UserDto>)