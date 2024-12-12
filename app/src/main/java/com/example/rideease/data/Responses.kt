package com.example.rideease.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimateResponse(
    val origin: Location,
    val destination: Location,
    val distance: Double,
    val duration: String,
    val options: List<RideOption>
) : Parcelable

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double
) : Parcelable

@Parcelize
data class RideOption(
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: Review,
    val value: Double
) : Parcelable

@Parcelize
data class Review(
    val rating: Double,
    val comment: String
) : Parcelable

data class ConfirmRideResponse(
    val success: Boolean
)

data class RideHistoryResponse(
    val customer_id: String,
    val rides: List<Ride>
)

data class Ride(
    val id: Int,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double
)
