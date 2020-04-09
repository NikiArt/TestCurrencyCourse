package com.nikitaboyko.testcurrencycourse.model.entities

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs")
class ValCurs {

    @field:ElementList(inline = true, entry = "Valute")
    var courses = mutableListOf<CurrencyCourse>()
}