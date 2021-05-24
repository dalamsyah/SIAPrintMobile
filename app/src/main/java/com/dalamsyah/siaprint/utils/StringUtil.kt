package com.dalamsyah.siaprint.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

object StringUtil {

    fun safeResult(){

    }

    fun capitalizeString(str: String): String {
        var retStr = str
        try { // We can face index out of bound exception if the string is null
            retStr = str.substring(0, 1).uppercase() + str.substring(1)
        } catch (e: Exception) {
        }
        return retStr
    }

}

val gson = Gson()

// Convert a Map to an object
inline fun <reified T> Map<String, Any>.toObject(): T {
    return convert()
}

// Convert an object to a Map
fun <T> T.toMap(): Map<String, Any> {
    return convert()
}

// Convert an object of type T to type R
inline fun <T, reified R> T.convert(): R {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<R>() {}.type)
}

fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>) : T? {
    //Get default constructor
    val constructor = clazz.constructors.first()

    //Map constructor parameters to map values
    val args = constructor
        .parameters
        .map { it to map[it.name] }
        .toMap()

    //return object from constructor call
    return null //constructor.callBy(args)
}