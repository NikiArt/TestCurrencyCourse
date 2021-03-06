package com.nikitaboyko.testcurrencycourse.model.di

import com.nikitaboyko.testcurrencycourse.ui.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, CacheModule::class])
interface AppComponent {

    fun inject(viewModel: MainViewModel)

}