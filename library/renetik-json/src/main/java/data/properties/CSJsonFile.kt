package renetik.android.json.data.properties

import renetik.android.json.data.CSJsonData
import renetik.android.json.extensions.getString
import renetik.android.json.extensions.put
import java.io.File

class CSJsonFile(val data: CSJsonData, private val key: String) {
    var file: File?
        get() = File(data.getString(key))
        set(file) = data.put(key, file?.toString())

    val value: File get() = file!!

    override fun toString() = "$file"
}