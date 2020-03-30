package com.niksatyr.randomuser.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserDto(
    val location: Location,
    val name: Name,
    @SerializedName("dob") val dateOfBirth: DateOfBirth,
    @SerializedName("picture") val photoUrls: PhotoUrls,
    val email: String,
    val phone: String
) {

    data class Name(
        val title: String,
        val first: String,
        val last: String
    )

    data class DateOfBirth(
        val date: Date,
        val age: Int
    )

    data class PhotoUrls(
        val large: String,
        val medium: String,
        val thumbnail: String
    )

    data class Location(
        val street: Street,
        val city: String,
        val state: String,
        val postcode: String
    )

    data class Street(
        val number: Int,
        val name: String
    )
}