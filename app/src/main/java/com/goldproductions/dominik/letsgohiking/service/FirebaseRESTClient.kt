package com.goldproductions.dominik.letsgohiking.service

import com.goldproductions.dominik.letsgohiking.model.Route
import com.goldproductions.dominik.letsgohiking.model.RouteData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FirebaseRESTClient {

    @POST("routes.json")
    fun saveRoute(@Body routeData: RouteData): Call<Unit>

    @GET("routes.json")
    fun getRoutes(): Call<MutableList<Route>>

}