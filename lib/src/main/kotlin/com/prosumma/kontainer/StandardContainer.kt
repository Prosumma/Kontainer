package com.prosumma.kontainer

class StandardContainer(val store: Store): Container {
    override fun <T> deserialize(key: Key, deserialize: (String) -> T): T? =
        store[key]?.let(deserialize)

    override fun <T> serialize(key: Key, value: T, serialize: (T) -> String) =
        synchronized(key) {
            store[key] = serialize(value)
        }
}