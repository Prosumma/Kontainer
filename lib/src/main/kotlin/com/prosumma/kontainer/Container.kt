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
    /**
     * If JSON exists for the given [Key], deserialize it using
     * the passed `deserialize` lambda. Otherwise return `null`.
     *
     * Don't use this method. It exists because of type erasure.
     * Instead, use one of the subscripts, e.g.,
     *
     * ```kotlin
     * val s: String? = container[key]
     * ```
     */
    fun <T> deserialize(key: Key, deserialize: (String) -> T): T?

    /**
     * Serialize the given `value` using the `serialize` lambda and
     * store the JSON at `key`.
     *
     * Don't use this method. It exists because of type erasure.
     * Instead, use the subscript setter:
     *
     * ```kotlin
     * container[key] = 13
     * ```
     */
    fun <T> serialize(key: Key, value: T, serialize: (T) -> String)
}

/**
 * Gets the value at the given key or `null`
 */
inline operator fun <reified T> Container.get(key: Key): T? =
    deserialize(key, Json::decodeFromString)

/**
 * Gets the value at the given key. If it does not exist, the value of `default`
 * is written into the container and then returned.
 */
inline operator fun <reified T> Container.get(key: Key, noinline default: () -> T): T =
    this[key] ?: run {
        synchronized(key) {
            val value = default()
            this[key] = value
            value
        }
    }

/**
 * Sets a value in the container at the given key.
 */
inline operator fun <reified T> Container.set(key: Key, value: T) =
    synchronized(key) {
        serialize(key, value, Json::encodeToString)
    }