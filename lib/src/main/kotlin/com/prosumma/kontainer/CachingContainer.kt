package com.prosumma.kontainer

/**
 * A `Container` which caches unserialized values
 * for faster access.
 */
class CachingContainer(private val store: Store): Container {
    private class Holder<T>(val value: T)

    private val cache: MutableMap<Key, Holder<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    override fun <T> deserialize(key: Key, deserialize: (String) -> T): T? =
        synchronized(key) {
            cache[key]?.let {
                it.value as T
            } ?: store[key]?.let { json ->
                val value = deserialize(json)
                cache[key] = Holder(value)
                value
            }
        }

    override fun <T> serialize(key: Key, value: T, serialize: (T) -> String) =
        synchronized(key) {
            store[key] = serialize(value)
            cache[key] = Holder(value)
        }

    /**
     * Remove an element from the cache, or all elements
     * if no [Key] is passed.
     *
     * Note that this does not remove the element from the
     * underlying store.
     *
     * Use of this method should be avoided except in low-memory
     * conditions.
     */
    fun purge(key: Key? = null) {
        key?.run {
            synchronized(key) {
                cache.remove(key)
            }
        } ?: run {
            cache.clear()
        }
    }
}