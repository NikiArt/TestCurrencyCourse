package com.nikitaboyko.testcurrencycourse.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitaboyko.testcurrencycourse.App
import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse
import com.nikitaboyko.testcurrencycourse.model.repositories.ICurrencyCourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : ViewModel() {

    val updateOperation = MutableLiveData<MutableList<CurrencyCourse>>()
    val convertOperation = MutableLiveData<Double>()
    val updateAdapters = MutableLiveData<Int>()
    var courses = mutableListOf<CurrencyCourse>()

    @Inject
    lateinit var courseRepo: ICurrencyCourseRepository

    init {
        App.instance.getAppComponent().inject(this)
        updateCourses()
        updateOperation.observeForever {
            courses.clear()
            courses = it
            updateAdapters.postValue(1)
        }
    }

    fun updateCourses() {
        GlobalScope.launch(Dispatchers.IO) {
            courseRepo.getCurrencyCourses(updateOperation)
        }

    }

    fun getResult(from: Int, to: Int, value: Double) {
        if (courses.size != 0) {
            GlobalScope.launch(Dispatchers.IO) {
                courseRepo.getCourse(
                    courses[from].charCode,
                    courses[to].charCode,
                    value,
                    convertOperation
                )
            }
        }
    }
}
