package com.aviraldg.trackshack.models

import com.aviraldg.trackshack.util.ParseValue
import com.parse.ParseClassName
import com.parse.ParseGeoPoint

@ParseClassName("Item")
class Item : BaseModel() {
    var name by ParseValue<String>()
    var location by ParseValue(ParseGeoPoint(0.0, 0.0))
}
