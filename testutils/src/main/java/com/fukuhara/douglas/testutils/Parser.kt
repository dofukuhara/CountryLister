package com.fukuhara.douglas.testutils

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.File
import java.io.FileReader

object Parser {
    fun <T> jsonToObject(obj: Any, jsonFile: String, clazz: Class<T>): T {
        val file: File? = obj.javaClass.classLoader?.let {
            File(it.getResource(jsonFile).path)
        }

        val jsonReader = JsonReader(FileReader(file))
        return Gson().fromJson(jsonReader, clazz)
    }
}