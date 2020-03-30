package com.niksatyr.randomuser.dto

import com.google.gson.annotations.SerializedName

data class UsersResponseDto(@SerializedName("results") val users: List<UserDto>)