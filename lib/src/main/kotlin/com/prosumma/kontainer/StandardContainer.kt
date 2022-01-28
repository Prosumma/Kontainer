package com.prosumma.kontainer

/**
 * The standard [Container]. This is the default container used by
 * [Kontainer] unless one is passed explicitly.
 *
 * Thread safety is deferred to the underlying [Store].
 */
class StandardContainer(val store: Store): Container {
    override fun <T> deserialize(key: Key, deserialize: (String) -> T): T? =
        store[key]?.let(deserialize)

    override fun <T> serialize(key: Key, value: T, serialize: (T) -> String) {
        store[key] = serialize(value)
    }
}