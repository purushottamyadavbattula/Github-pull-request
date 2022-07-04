package com.yellowai.git_pull_requests.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

object GsonSingleton {

    val deSerializerGson: Gson
    val serializerGson: Gson

    val booleanTypeAdapter: TypeAdapter<Boolean> = object : TypeAdapter<Boolean>() {

        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Boolean?) {
            if (value == null) {
                out.nullValue()
            } else {
                out.value(value)
            }
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Boolean? {
            val peek = `in`.peek()
            when (peek) {
                JsonToken.BOOLEAN -> return `in`.nextBoolean()
                JsonToken.NULL -> {
                    `in`.nextNull()
                    return null
                }
                JsonToken.NUMBER -> return `in`.nextInt() != 0
                JsonToken.STRING -> {
                    val string = `in`.nextString()
                    if (string.equals("yes", ignoreCase = true) || string.equals(
                            "true",
                            ignoreCase = true
                        ) || string.equals("1", ignoreCase = true)
                    ) {
                        return true
                    } else if (string.equals("no", ignoreCase = true) || string.equals(
                            "false",
                            ignoreCase = true
                        ) || string.equals("0", ignoreCase = true)
                    ) {
                        return false
                    }
                    throw IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek)
                }
                else -> throw IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek)
            }
        }
    }

    init {
        deSerializerGson = GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, booleanTypeAdapter)
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, booleanTypeAdapter)
            .create()

        serializerGson = GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, booleanTypeAdapter)
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, booleanTypeAdapter)
            .create()
    }
}