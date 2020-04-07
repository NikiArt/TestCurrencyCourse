package com.nikitaboyko.testcurrencycourse.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(courses: MutableList<CurrencyCourse>)

    @Query("SELECT * FROM courses WHERE currency = :currency")
    fun getLastCourse(currency: String): CurrencyCourse

    @Query("SELECT currency FROM courses ")
    fun getCurrencies(): MutableList<String>
}