package com.nikitaboyko.testcurrencycourse.model.di

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
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [CacheModule::class])
class ApiModule {

    @Named("testUrl")
    @Provides
    fun baseUrl(): String {
        return "http://www.cbr.ru/scripts/"
    }

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun getOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun getRetrofitApi(
        okHttpClient: OkHttpClient,
        @Named("testUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun createApi(retrofit: Retrofit): ApiRequests {
        return retrofit.create<ApiRequests>(ApiRequests::class.java)
    }

    @Singleton
    @Provides
    fun getData(apiRequests: ApiRequests, cache: RoomCache): ICurrencyCourseRepository {
        return CurrencyCourseRepository(apiRequests, cache)
    }
}