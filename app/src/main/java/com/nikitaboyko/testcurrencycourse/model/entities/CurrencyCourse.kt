package com.nikitaboyko.testcurrencycourse.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "courses")
class CurrencyCourse {

    @SerializedName("ID")
    @PrimaryKey
    var id = ""
    @SerializedName("NumCode")
    @ColumnInfo(name = "currency_code")
    var currencyCode = 0
    @SerializedName("CharCode")
    @ColumnInfo(name = "char_code")
    var charCode = ""
    @SerializedName("Name")
    var currency = ""
    @SerializedName("Value")
    var value = ""
}