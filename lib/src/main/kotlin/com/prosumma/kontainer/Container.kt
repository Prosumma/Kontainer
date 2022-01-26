package com.prosumma.kontainer

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A `Container` serializes and deserializes instances from
 * its underlying [Store].
 *
 * Two implementations of `Container` are provided. The first is
 * [StandardContainer] and the second is [CachingContainer]. Both
 * take an underlying [Store] instance to provide persistence.
 */
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
    synchronized(key) {
        serialize(key, value, Json::encodeToString)
    }