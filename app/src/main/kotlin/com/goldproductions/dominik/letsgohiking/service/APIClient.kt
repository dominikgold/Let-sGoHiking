package com.goldproductions.dominik.letsgohiking.service

import com.goldproductions.dominik.letsgohiking.model.GPSPointList
import com.goldproductions.dominik.letsgohiking.model.RouteList
import com.goldproductions.dominik.letsgohiking.model.RouteWithPoints
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIClient {

    @POST("routes/")
    fun saveRoute(@Body route: RouteWithPoints): Call<Unit>

    @GET("routes/")
    fun getRoutes(): Call<RouteList>

    @GET("routes/{id}/points")
    fun getPointsForRoute(@Path("id") id: Int): Call<GPSPointList>

}