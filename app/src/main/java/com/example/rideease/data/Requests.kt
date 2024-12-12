package com.example.rideease.data

data class EstimateRequest(
    val customer_id: String,
    val origin: String,
    val destination: String
)

data class ConfirmRideRequest(
    val customer_id: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double
)

data class Driver(
    val id: Int,
    val name: String
)

data class RideHistoryRequest(
    val customer_id: String,
    val rides: Int
)
