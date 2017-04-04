package com.goldproductions.dominik.letsgohiking.examples

import android.util.Log
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class RxJavaTest(val textView: TextView) {

    val TAG = "RxJavaTest"

    fun helloPlanets() {
        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { next ->
                    next.contains("Earth", ignoreCase = true)
                }
                .map { next ->
                    next.replace("Earth", "World", ignoreCase = true)
                }
                // createSecondObservable() is an observable that simply emits a single Int = 1
                .zipWith<Int, String>(createSecondObservable(), BiFunction { nextString: String, nextInt: Int ->
                    nextString + nextInt
                })
                .subscribe({ next: String ->
                    // now next equals "Hello World1"
                    // only possible on UI thread
                    textView.text = textView.text.toString() +  "\n" + next
                    // print to the android monitor
                    Log.d(TAG, next)
                }, { error: Throwable ->
                    // do nothing for now
                }, {
                    // observable has completed
                    Log.d(TAG, "Emission completed!")
                })
    }

    fun createObservable(): Observable<String> {
        val planets = arrayOf("Mercury", "Venus", "Earth")
        return Observable.create<String> { subscriber: ObservableEmitter<String> ->
            for (planet in planets) {
                // simulate blocking network request by sleeping for 1000 ms
                Thread.sleep(1000)
                val helloPlanet = "Hello " + planet
                subscriber.onNext(helloPlanet)
            }
            subscriber.onComplete()
        }
    }

    fun createSecondObservable(): Observable<Int> {
        return Observable.just(1)
    }
}