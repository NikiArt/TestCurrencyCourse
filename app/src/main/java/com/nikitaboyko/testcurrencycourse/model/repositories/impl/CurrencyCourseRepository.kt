package com.nikitaboyko.testcurrencycourse.model.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.nikitaboyko.testcurrencycourse.App
import com.nikitaboyko.testcurrencycourse.model.api.ApiRequests
import com.nikitaboyko.testcurrencycourse.model.db.RoomCache
import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse
import com.nikitaboyko.testcurrencycourse.model.repositories.ICurrencyCourseRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.apache.commons.math3.util.Precision

class CurrencyCourseRepository(
    val api: ApiRequests,
    val cache: RoomCache
) : ICurrencyCourseRepository {

    private val disposable = CompositeDisposable()

    override fun getCurrencyCourses(successOperation: MutableLiveData<MutableList<CurrencyCourse>>) {

        disposable.add(cache.getCourses().subscribeOn(Schedulers.io())
            .subscribe { result ->
                successOperation.postValue(result)
            })

        if (App.instance.isOffline()) return

        disposable.add(api.getCourses()
            .subscribeOn(Schedulers.io())
            .map { mainObject ->
                val result = mainObject.courses
                if (result.find { it.currencyCode == 643 } == null) {
                    result.add(0,
                        CurrencyCourse().apply {
                            currencyCode = 643
                            charCode = "RUB"
                            currency = "Российский рубль"
                            value = "1"
                        }
                    )
                }
                result.sortBy { it.currencyCode }
                cache.addCourses(result).subscribe()
                return@map result
            }
            .subscribeBy(
                onSuccess = { successOperation.postValue(it) },
                onError = { it.printStackTrace() }
            ))
    }

    override fun getCourse(
        from: String,
        to: String,
        sum: Double,
        successOperation: MutableLiveData<Double>
    ) {
        disposable.add(Single.zip(
            cache.getCourse(from),
            cache.getCourse(to),
            BiFunction { resultFrom: CurrencyCourse, resultTo: CurrencyCourse ->
                val valueFrom = resultFrom.value.replace(",", ".").toDouble()
                val valueTo = resultTo.value.replace(",", ".").toDouble()
                return@BiFunction (sum * (valueFrom / resultFrom.nominal) / (valueTo / resultTo.nominal))
            })
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { successOperation.postValue(Precision.round(it, 2)) },
                onError = {
                    successOperation.postValue(0.0)
                    it.printStackTrace()
                }
            ))

    }

    override fun dispose() {
        disposable.dispose()
    }
}