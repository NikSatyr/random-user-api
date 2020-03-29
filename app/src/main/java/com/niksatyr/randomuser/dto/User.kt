package com.niksatyr.randomuser.dto

import com.squareup.moshi.Json
import java.util.*

data class User(
    var name: String,
    var surname: String,
    @Json(name = "date") var dateOfBirth: Date,
    var email: String,
    var photoUrl: String
)