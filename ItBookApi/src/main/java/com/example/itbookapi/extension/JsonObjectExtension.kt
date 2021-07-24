package com.example.itbookapi.extension

import com.google.gson.JsonObject

fun JsonObject?.toJoinedString(): String {
    if (this == null) {
        return ""
    }
    return keySet().asSequence().map {
        it + " : " + this.get(it).asString
    }.joinToString("\n")
}