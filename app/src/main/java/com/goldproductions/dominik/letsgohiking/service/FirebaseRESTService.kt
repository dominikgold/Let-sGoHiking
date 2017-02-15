package com.goldproductions.dominik.letsgohiking.service

import com.goldproductions.dominik.letsgohiking.model.Route
import com.goldproductions.dominik.letsgohiking.model.RouteData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.exceptions.Exceptions

class FirebaseRESTService(private val client: FirebaseRESTClient) {

    /**
     * returns a Completable object that sends a synchronous POST request and signals whether the save was successful or
     * not
     */
    fun saveRoute(routeData: RouteData): Completable {
        return Completable.create { subscriber ->
            val call = client.saveRoute(routeData)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    subscriber.onComplete()
                } else {
                    subscriber.onError(Throwable(response.message()))
                }
            } catch (t: Throwable) {
                Exceptions.propagate(t)
            }
        }
    }

    /**
     * returns a Single object that requests all routes from the database synchronously.
     */
    fun getRoutes(): Single<List<Route>> {
        return Single.create { subscriber ->
            val call = client.getRoutes()
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    subscriber.onSuccess(response.body())
                } else {
                    subscriber.onError(Throwable(response.message()))
                }
            } catch (t: Throwable) {
                Exceptions.propagate(t)
            }
        }
    }

}