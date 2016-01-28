package com.aviraldg.trackshack.models

import com.aviraldg.trackshack.util.ParseValue
import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Pipeline")
class Pipeline : BaseModel() {
    var name by ParseValue<String>()

}