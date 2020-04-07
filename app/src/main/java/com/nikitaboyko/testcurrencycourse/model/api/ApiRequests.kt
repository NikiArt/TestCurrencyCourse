package com.nikitaboyko.testcurrencycourse.model.api

import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse
import io.reactivex.Single
import retrofit2.http.GET

interface ApiRequests {

    @GET("https://api.exchangeratesapi.io/latest?base=EUR")
    fun getCourses(): Single<MutableList<CurrencyCourse>>

}