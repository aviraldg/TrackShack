package com.aviraldg.trackshack.models

import com.aviraldg.trackshack.util.ParseValue
import com.parse.ParseClassName

@ParseClassName("User")
class User : BaseModel() {
    var name by ParseValue<String>()
}
