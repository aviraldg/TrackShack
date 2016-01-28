package com.aviraldg.trackshack.util

import com.google.android.gms.maps.MapsInitializer
import com.parse.ParseObject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ParseValue<T>(val initialValue: T? = null) : ReadWriteProperty<ParseObject, T> {
    override operator fun getValue(thisRef: ParseObject, property: KProperty<*>): T {
        return thisRef.get(property.name) as T
    }

    override operator fun setValue(thisRef: ParseObject, property: KProperty<*>, value: T) {
        thisRef.put(property.name, value)
    }
}