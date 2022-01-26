package com.prosumma.kontainer

/**
 * A [Store] which keeps its JSON in memory. This is suitable
 * primarily for testing.
 */
class MemoryStore: Store {
    private val memory: MutableMap<Key, String> = mutableMapOf()
    override operator fun get(key: Key): String? = memory[key]
    override operator fun set(key: Key, json: String) {
        memory[key] = json
    }
}