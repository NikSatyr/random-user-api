package com.niksatyr.randomuser.model

import com.niksatyr.randomuser.dto.UserDto
import java.util.*

data class User(
    var fullName: String,
    var birthday: Date,
    var age: Int,
    var thumbnailPhotoUrl: String,
    var mediumPhotoUrl: String,
    var largePhotoUrl: String,
    var email: String,
    var phone: String,
    var address: String
) {

    class Factory {

        companion object {

            fun create(userDto: UserDto): User {
                return User(
                    fullName = "${userDto.name.title} ${userDto.name.first} ${userDto.name.last}",
                    birthday = userDto.dateOfBirth.date,
                    age = userDto.dateOfBirth.age,
                    thumbnailPhotoUrl = userDto.photoUrls.thumbnail,
                    mediumPhotoUrl = userDto.photoUrls.medium,
                    largePhotoUrl = userDto.photoUrls.large,
                    email = userDto.email,
                    phone = userDto.phone,
                    address = "${userDto.location.city}, ${userDto.location.state}, " +
                            "${userDto.location.street}, ${userDto.location.postcode}"
                )
            }

        }
    }

}