package com.aviraldg.trackshack.api

import android.content.Context
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import com.aviraldg.trackshack.api.models.AuthToken

object AuthUtil {
    fun setApiKey(context: Context, apiKey: String?) {
        val pm = PreferenceManager.getDefaultSharedPreferences(context)
        val spe = pm.edit()
        spe.putString("apiKey", apiKey)
        spe.commit()
    }

    fun getApiKey(context: Context): String? {
        val pm = PreferenceManager.getDefaultSharedPreferences(context)
        return pm.getString("apiKey", null)
    }

    fun isAuthenticated(context: Context): Boolean {
        val pm = PreferenceManager.getDefaultSharedPreferences(context)
        return pm.contains("apiKey")
    }
}
