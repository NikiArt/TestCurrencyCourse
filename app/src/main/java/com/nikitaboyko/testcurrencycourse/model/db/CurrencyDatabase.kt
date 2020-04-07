package com.nikitaboyko.testcurrencycourse.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikitaboyko.testcurrencycourse.model.db.dao.CurrencyDao
import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse


@Database(entities = [CurrencyCourse::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "currency_course.db"
    }

    abstract fun getCurrencyDao(): CurrencyDao


}