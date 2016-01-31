package com.aviraldg.trackshack.util

import android.support.v4.app.FragmentTransaction
import com.aviraldg.trackshack.R

fun md5(md5: String): String? {
    try {
        val md = java.security.MessageDigest.getInstance("MD5")
        val array = md.digest(md5.toByteArray())
        val sb = StringBuffer()
        for (i in array.indices) {
            sb.append(Integer.toHexString(array[i].toInt() and 255 or 256).substring(1, 3))
        }
        return sb.toString()
    } catch (e: java.security.NoSuchAlgorithmException) {
    }

    return null
}

fun animate(ft: FragmentTransaction) {
    ft.setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom,
            R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
}
