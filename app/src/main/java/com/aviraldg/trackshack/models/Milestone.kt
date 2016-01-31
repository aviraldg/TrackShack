package com.aviraldg.trackshack.models

import com.aviraldg.trackshack.util.ParseValue
import com.parse.ParseClassName
import com.parse.ParseUser

@ParseClassName("Milestone")
class Milestone : BaseModel() {
    var name by ParseValue<String?>()
    var order by ParseValue<Int?>()
    var owner by ParseValue<ParseUser?>()
}