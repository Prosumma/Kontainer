package com.prosumma.kontainer

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface Container {
    fun <T> deserialize(key: Key, deserialize: (String) -> T): T?
    fun <T> serialize(key: Key, value: T, serialize: (T) -> String)
}

inline operator fun <reified T> Container.get(key: Key): T? =
    deserialize(key, Json::decodeFromString)

inline operator fun <reified T> Container.get(key: Key, noinline default: () -> T): T =
    this[key] ?: run {
        synchronized(key) {
            val value = default()
            this[key] = value
            value
        }
    }

inline operator fun <reified T> Container.set(key: Key, value: T) =
    serialize(key, value, Json::encodeToString)