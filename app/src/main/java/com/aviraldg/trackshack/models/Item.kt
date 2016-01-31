package com.aviraldg.trackshack.models

import com.aviraldg.trackshack.util.ParseValue
import com.parse.ParseClassName
import com.parse.ParseGeoPoint

@ParseClassName("Item")
class Item : BaseModel() {
    var name by ParseValue<String>()
    var desc by ParseValue<String>()
    var cemail by ParseValue<String>()
    var cphone by ParseValue<String>()
    var milestone by ParseValue<Milestone>()
}
