package com.prosumma.kontainer

/**
 * A `Key` represents an entry in the underlying `Container`.
 *
 * In general, it is not necessary to use keys directly. In fact,
 * the entire point of Kontainer is to avoid the use of strings
 * and keys, and instead use hierarchical dot syntax to access
 * values:
 *
 * ```kotlin
 * val width = kontainer.config.metrics.width
 * ```
 *
 * This value is represented as `/config/metrics/width` in the underlying
 * container.
 */
class Key internal constructor(private val value: String) {
    companion object {
        const val SEPARATOR = "/"
        private val keys: MutableMap<String, Key> = mutableMapOf()
        fun create(value: String): Key =
            keys.getOrPut(value) { Key(value) }
        fun create(name: Name): Key = create(name.toString())
    }

    init {
        // Validates each Name component of the key
        value.split(SEPARATOR).map(::Name)
    }

    operator fun plus(name: String): Key = create(value + SEPARATOR + name)
    operator fun plus(name: Name): Key = plus(name.toString())

    override fun equals(other: Any?): Boolean =
        other != null && other is Key && other.value == value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value
}