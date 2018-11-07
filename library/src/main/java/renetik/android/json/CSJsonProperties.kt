package renetik.android.json

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import renetik.android.location.asLatLng
import renetik.java.collections.CSList
import renetik.java.collections.CSMap
import renetik.android.lang.CSLang.list
import java.io.File
import kotlin.reflect.KClass

class CSJsonStringProperty(val data: CSJsonData, private val key: String) {
    var string: String?
        get() = data.getString(key)
        set(value) = data.put(key, value)

    override fun toString(): String {
        return string ?: super.toString()
    }
}

class CSJsonBoolProperty(val data: CSJsonData, private val key: String) {
    var bool: Boolean?
        get() = data.getBoolean(key)
        set(value) = data.put(key, value)
    val isTrue: Boolean get() = bool ?: false
}

class CSJsonFileProperty(val data: CSJsonData, private val key: String) {
    var value: File?
        get() = File(data.getString(key))
        set(file) = data.put(key, file?.toString())
}

@Suppress("unchecked_cast")
class CSJsonLocationProperty(val data: CSJsonData, private val key: String) {
    var latLng: LatLng?
        get() = (data.getList(key) as? List<Double>)?.let { LatLng(it[0], it[1]) }
        set(latLng) = data.put(key, list(latLng?.latitude, latLng?.longitude))

    fun set(location: Location) {
        latLng = location.asLatLng()
    }
}

@Suppress("unchecked_cast")
class CSJsonListProperty<T : CSJsonData>(val data: CSJsonData, val type: KClass<T>,
                                         val key: String) {
    var list: CSList<T>
        get() = createList(type.java, data.getList(key) as List<CSMap<String, Any?>>?)
        set(list) = list.forEach { item -> add(item) }

    val last: T? get() = list.last()
    val empty get() = size() == 0

    fun add(item: T) = data.getList(key)?.add(item.getJsonDataMap())
            ?: data.put(key, list(item.getJsonDataMap()))

    fun remove(item: T) = data.getList(key)?.remove(item.getJsonDataMap())
    fun removeLast() = data.getList(key)?.removeLast()
    fun size() = data.getList(key)?.size ?: let { 0 }
}