package com.prosumma.kontainer

/**
 * A key-value store in which the keys
 * are of type [Key] and the values are
 * [String]s of JSON.
 *
 * A [Store] is usually passed to a [Container]
 * instance to provide persistence.
 */
interface Store {
    operator fun get(key: Key): String?
    operator fun set(key: Key, json: String)
}