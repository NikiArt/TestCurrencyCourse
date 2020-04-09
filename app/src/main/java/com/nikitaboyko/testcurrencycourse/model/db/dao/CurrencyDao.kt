package com.nikitaboyko.testcurrencycourse.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nikitaboyko.testcurrencycourse.model.entities.CurrencyCourse
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(courses: MutableList<CurrencyCourse>)

    @Query("SELECT * FROM courses WHERE char_code = :currency")
    fun getLastCourse(currency: String): Single<CurrencyCourse>

    @Query("SELECT * FROM courses")
    fun getCourses(): Single<MutableList<CurrencyCourse>>

}