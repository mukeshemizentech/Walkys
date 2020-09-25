package com.walky.utils

import com.google.gson.Gson

class JsonDeserializer {

    companion object {
        private val gson = Gson()

        fun <T> deserializeJson(jsonString: String?, type: Class<T>?): T? {
            var result: T? = null
            result = gson.fromJson(jsonString, type)
            return result
        }

        fun serializeJson(type: Any?): String? {
            var result: String? = null
            result = gson.toJson(type)
            return result
        }
    }

}