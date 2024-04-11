package sb.park.bus.data.util

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

internal inline fun <reified T> JsonElement.toList(): List<T> {
    return Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)
}