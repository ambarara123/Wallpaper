package com.example.wallpaper.rxjava

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

private val array = arrayListOf(1, 2, 4, 6, 7, 12, 32, 61, 23, 21)

private val observable = Observable.fromArray(array)

@SuppressLint("CheckResult")
fun main() {
    observable.subscribe {
        println(it.toString())
    }

    val observable = Observable.just(1, 2, 3, 4, 5)
        .filter {
            it % 2 == 0
        }
        .map {
            it * 2
        }
        /*.flatMap {

        }*/
        .subscribe {
            println(it.toString())
        }
    val observable2 = Observable.just("item1", "item2", "item3")
        .doOnNext {

        }
        .subscribe {
            println(it)
        }


}
