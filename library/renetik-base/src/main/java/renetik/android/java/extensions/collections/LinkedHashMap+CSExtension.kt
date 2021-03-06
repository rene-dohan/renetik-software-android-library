package renetik.android.java.extensions.collections

fun <K, V> linkedMap() = LinkedHashMap<K, V>()
fun <K, V> LinkedHashMap<K, V>.getValue(index: Int) = list(values)[index]
fun <K, V> LinkedHashMap<K, V>.index(key: K) = list(keys).indexOf(key)
fun <K, V> LinkedHashMap<K, V>.removeAt(i: Int) = remove(list(keys)[i])