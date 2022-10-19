package com.dalamsyah.siaprint.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.dalamsyah.siaprint.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import java.text.NumberFormat
import java.util.*
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

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}

private val isDebug = BuildConfig.DEBUG
private const val TAG = "DEBUGGG"

fun printLog(msg: String){
    if (isDebug) Log.d(TAG, msg)
}

object PrintLog{

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

fun String.convertRupiah(): String {
    val localId = Locale("in", "ID")
    val formatter = NumberFormat.getCurrencyInstance(localId)
    var string = "0"
    try {
        string = this as String
    }catch (e: Exception){
        Logger.d(e)
    }
    return formatter.format(string.toDouble()).toString().replace("Rp", "Rp ").replace(",00","")
}

fun convertRupiah(number: Double): String {
    val localId = Locale("in", "ID")
    val formatter = NumberFormat.getCurrencyInstance(localId)
    return formatter.format(number).toString().replace("Rp", "Rp ").replace(",00","")
}