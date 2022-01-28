package com.prosumma.kontainer

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

    operator fun plus(name: Name): Key = create(value + SEPARATOR + name.toString())
    operator fun plus(name: String): Key = this + Name(name)

    override fun equals(other: Any?): Boolean =
        other != null && other is Key && other.value == value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value
}