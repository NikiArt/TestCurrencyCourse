package com.nikitaboyko.testcurrencycourse.model.api

import com.nikitaboyko.testcurrencycourse.model.entities.ValCurs
import io.reactivex.Single
import retrofit2.http.GET

interface ApiRequests {

    @GET("XML_daily.asp")
    fun getCourses(): Single<ValCurs>

}