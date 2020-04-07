package com.nikitaboyko.testcurrencycourse.model.di

import androidx.room.Room
import com.nikitaboyko.testcurrencycourse.App
import com.nikitaboyko.testcurrencycourse.model.db.CurrencyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun getDatabase(): CurrencyDatabase {
        return synchronized(CurrencyDatabase) {
            Room.databaseBuilder<CurrencyDatabase>(
                App.instance,
                CurrencyDatabase::class.java,
                CurrencyDatabase.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}