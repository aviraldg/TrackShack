package com.aviraldg.trackshack.models

import android.net.Uri
import com.aviraldg.trackshack.util.ParseValue
import com.aviraldg.trackshack.util.md5
import com.parse.ParseClassName
import com.parse.ParseQuery
import com.parse.ParseUser

@ParseClassName("User")
class User(val user: ParseUser) {
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

    // Needed for AutocompleteTextView in MilestoneFragment
    override fun toString(): String {
        return user.username ?: "?"
    }
}

val ParseUser.image: Uri?
    get() {
        return Uri.parse("http://www.gravatar.com/avatar/${md5(email ?: username)}?d=retro")
    }
