package com.example.rideease.data

import retrofit2.http.*

interface ApiService {
    @POST("/ride/estimate")
    suspend fun estimateRide(@Body request: EstimateRequest): EstimateResponse

    @PATCH("/ride/confirm")
    suspend fun confirmRide(@Body request: ConfirmRideRequest): ConfirmRideResponse

    @GET("/ride/{customer_id}")
    suspend fun getRideHistory(
        @Path("customer_id") customerId: String,
        @Query("driver_id") driverId: Int?
    ): RideHistoryResponse

}
