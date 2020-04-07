package com.nikitaboyko.testcurrencycourse

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.nikitaboyko.testcurrencycourse.model.di.AppComponent
import com.nikitaboyko.testcurrencycourse.model.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
        instance = this
    }

    fun isOnline(): Boolean {
        val cm =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

}