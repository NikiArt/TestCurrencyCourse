package com.nikitaboyko.testcurrencycourse.model.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nikitaboyko.testcurrencycourse.model.api.ApiRequests
import com.nikitaboyko.testcurrencycourse.model.db.RoomCache
import com.nikitaboyko.testcurrencycourse.model.repositories.ICurrencyCourseRepository
import com.nikitaboyko.testcurrencycourse.model.repositories.impl.CurrencyCourseRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [CacheModule::class])
class ApiModule {

    @Named("testUrl")
    @Provides
    fun baseUrl(): String {
        return "http://192.168.0.36:61217/api/"
    }

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun getOkHttpClient(loggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @get:Provides
    val gson: Gson
        get() = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Provides
    fun getRetrofitApi(
        okHttpClient: OkHttpClient?,
        gson: Gson?, @Named("testUrl") baseUrl: String?
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun createApi(retrofit: Retrofit): ApiRequests {
        return retrofit.create<ApiRequests>(ApiRequests::class.java)
    }

    @Singleton
    @Provides
    fun getData(apiRequests: ApiRequests, cache: RoomCache): ICurrencyCourseRepository? {
        return CurrencyCourseRepository(apiRequests, cache)
    }
}