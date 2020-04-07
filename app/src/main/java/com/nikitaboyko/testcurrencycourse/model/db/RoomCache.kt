package com.nikitaboyko.testcurrencycourse.model.db

import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RoomCache(private val database: CurrencyDatabase) {


    fun addCourses(courses: MutableList<CurrencyCourse>): Completable {
        return Completable.fromAction {
            database.getCurrencyDao().insert(courses)
        }
            .subscribeOn(Schedulers.io())
    }

    fun getCourse(currency: String): Single<CurrencyCourse> {
        return Single.create { emitter ->
            val currencyCourse = database.getCurrencyDao().getLastCourse(currency)
            emitter.onSuccess(currencyCourse)
        }
    }

    fun getCurrencies(): Single<MutableList<String>> {
        return Single.create { emitter ->
            val currencies = database.getCurrencyDao().getCurrencies()
            emitter.onSuccess(currencies)
        }
    }

}