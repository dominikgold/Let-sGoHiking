package com.goldproductions.dominik.letsgohiking.mvp.base

import android.util.Log
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class RxJavaTest(val textView: TextView) {

    val TAG = "BaseActivity"

    var timeOfFirstEmission = 0L

//    fun helloPlanets() {
//        createObservable()
//                // this allows to execute our Observable code on a separate thread intended for I/O work (e.g. a network
//                // request) so that it doesn't block our main thread
//                .subscribeOn(Schedulers.io())
//                // RxAndroid hook that makes our Subscriber code execute on the Android UI thread
//                // this is necessary when we want to make changes to the UI based on the emissions of a data flow
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    next ->
//                    // consume the elements
//                    Log.d(TAG, next + " after " + (System.currentTimeMillis() - timeOfFirstEmission) + "ms")
//                }, {
//                    error ->
//                    // here goes all error handling
//                    Log.e(TAG, "encountered an error: " + error.message)
//                }, {
//                    // this is executed after onComplete() is called
//                    Log.d(TAG, "observable completed its emission after "
//                            + (System.currentTimeMillis() - timeOfFirstEmission) + "ms")
//                })
//    }
//
//    /**
//     * creates an observable that emits 3 Strings, one per second
//     */
//    fun createObservable(): Observable<String> {
//        val hello: String = "Hello "
//        val planets: Array<String> = arrayOf("Mercury", "Venus", "Earth")
//        timeOfFirstEmission = System.currentTimeMillis()
//        return Observable.create<String>({
//            subscriber ->
//            for (i in planets.indices) {
//                // sleeping simulates expensive network request
//                Thread.sleep(1000)
//                val helloPlanet: String = hello + planets[i]
//                subscriber.onNext(helloPlanet)
//            }
//            subscriber.onComplete()
//        })
//    }

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