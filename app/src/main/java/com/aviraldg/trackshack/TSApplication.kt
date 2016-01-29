package com.aviraldg.trackshack

import android.app.Application
import com.aviraldg.trackshack.models.Item
import com.aviraldg.trackshack.models.Milestone
import com.aviraldg.trackshack.models.Pipeline
import com.aviraldg.trackshack.models.User
import com.facebook.drawee.backends.pipeline.Fresco
import com.parse.Parse
import com.parse.ParseInstallation
import com.parse.ParseObject

class TSApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        arrayOf(Milestone::class.java,
                Pipeline::class.java,
                User::class.java,
                Item::class.java)
            .map {
                ParseObject.registerSubclass(it)
            }

        Fresco.initialize(this)

        Parse.enableLocalDatastore(this)
        Parse.initialize(this)

        ParseInstallation.getCurrentInstallation().saveInBackground()
    }
}
