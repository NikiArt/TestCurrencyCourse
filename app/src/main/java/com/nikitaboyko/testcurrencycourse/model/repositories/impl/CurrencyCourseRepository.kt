package com.nikitaboyko.testcurrencycourse.model.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.nikitaboyko.testcurrencycourse.model.api.ApiRequests
import com.nikitaboyko.testcurrencycourse.model.db.RoomCache
import com.nikitaboyko.testcurrencycourse.model.repositories.ICurrencyCourseRepository
import io.reactivex.schedulers.Schedulers

class CurrencyCourseRepository(
    val api: ApiRequests,
    val cache: RoomCache
) : ICurrencyCourseRepository {

    fun getCurrencyCourses(successOperation: MutableLiveData<MutableList<String>>) {
        api.getCourses()
            .subscribeOn(Schedulers.io())
            .map { it ->
                if (it.isEmpty()) {
                    cache.getCurrencies()
                        .subscribe { result ->
                            successOperation.postValue(result)
                        }
                } else {
                    cache.addCourses(it)
                    val courseList = mutableListOf<String>()
                    it.forEach {
                        courseList.add(it.currency)
                    }
                    successOperation.postValue(courseList)
                }
            }
    }

}