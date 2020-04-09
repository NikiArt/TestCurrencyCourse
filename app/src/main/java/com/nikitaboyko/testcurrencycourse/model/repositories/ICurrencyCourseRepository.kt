package com.nikitaboyko.testcurrencycourse.model.repositories

import androidx.lifecycle.MutableLiveData
import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse

interface ICurrencyCourseRepository {

    fun getCurrencyCourses(successOperation: MutableLiveData<MutableList<CurrencyCourse>>)
    fun getCourse(from: String, to: String, sum: Double, successOperation: MutableLiveData<Double>)
    fun dispose()
}