package com.aviraldg.trackshack.models

import com.aviraldg.trackshack.util.ParseValue
import com.parse.ParseClassName
import com.parse.ParseQuery
import com.parse.ParseUser

@ParseClassName("User")
class User : BaseModel() {
    companion object {
        fun usernameLike(username: String): ParseQuery<ParseUser> {
            val q = ParseUser.getQuery()
            return q.whereStartsWith("username", username)
        }

        fun assignedToMilestone(milestone: Milestone): ParseQuery<ParseUser> {
            val q = ParseUser.getQuery()
            return q.whereEqualTo("milestone", milestone)
        }
    }
    var name by ParseValue<String>()
    var username by ParseValue<String>()
}
