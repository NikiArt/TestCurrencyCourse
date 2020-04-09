package com.nikitaboyko.testcurrencycourse.model.db

import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse
import io.reactivex.Completable
import io.reactivex.Single

class RoomCache(private val database: CurrencyDatabase) {


    fun addCourses(courses: MutableList<CurrencyCourse>): Completable {
        return Completable.fromAction {
            database.getCurrencyDao().insert(courses)
        }
    }

    fun getCourse(currency: String): Single<CurrencyCourse> {
        return database.getCurrencyDao().getLastCourse(currency)
    }

    fun getCourses(): Single<MutableList<CurrencyCourse>> {
        return database.getCurrencyDao().getCourses()
        /*return Single.create { emitter ->
            val currencyCourses = database.getCurrencyDao().getCourses()
            emitter.onSuccess(currencyCourses)
        }*/
    }

}