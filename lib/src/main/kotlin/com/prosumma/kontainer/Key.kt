package com.prosumma.kontainer

class Key internal constructor(private val value: String) {
    companion object {
        private const val SEPARATOR = "/"
        private val keys: MutableMap<String, Key> = mutableMapOf()
        private fun createKey(value: String): Key =
            keys.getOrPut(value) { Key(value) }
        fun create(name: Name): Key = createKey(name.toString())
        fun create(name: String): Key = create(Name(name))
    }

    operator fun plus(name: Name): Key = createKey(value + SEPARATOR + name.toString())
    operator fun plus(name: String): Key = this + Name(name)

    override fun equals(other: Any?): Boolean =
        other != null && other is Key && other.value == value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value
}