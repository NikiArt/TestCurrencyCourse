package com.nikitaboyko.testcurrencycourse.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "Valute")
@Entity(tableName = "courses")
class CurrencyCourse {

    @field:Element(name = "NumCode")
    @PrimaryKey
    @ColumnInfo(name = "currency_code")
    var currencyCode = 0
    @field:Element(name = "CharCode")
    @ColumnInfo(name = "char_code")
    var charCode = ""
    @field:Element(name = "Name")
    @ColumnInfo(name = "currency")
    var currency = ""
    @field:Element(name = "Nominal")
    @ColumnInfo(name = "nominal")
    var nominal = 1
    @field:Element(name = "Value")
    @ColumnInfo(name = "value")
    var value = ""

}