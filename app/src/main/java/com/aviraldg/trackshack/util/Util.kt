package com.aviraldg.trackshack.util

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