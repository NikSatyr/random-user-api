package com.niksatyr.randomuser.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    val name: Name,
    @SerializedName("dob") val dateOfBirth: DateOfBirth,
    @SerializedName("picture") val photoUrls: PhotoUrls,
    val email: String,
    val phone: String,
    val location: Location
) : Parcelable {

    @Parcelize
    data class Name(
        val title: String,
        val first: String,
        val last: String
    ) : Parcelable

    @Parcelize
    data class DateOfBirth(
        val date: Date,
        val age: Int
    ) : Parcelable

    @Parcelize
    data class PhotoUrls(
        val large: String,
        val medium: String,
        val thumbnail: String
    ) : Parcelable

    @Parcelize
    data class Location(
        val city: String,
        val state: String,
        val street: String,
        val postcode: String
    ) : Parcelable
}