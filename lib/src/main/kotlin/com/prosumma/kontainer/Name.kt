package com.prosumma.kontainer

class Name(private val value: String) {
    companion object {
        fun validate(value: String): Boolean =
            value.isEmpty() || (value.isNotBlank() && !value.contains('/'))
    }

    init {
        if (!validate(value))
            throw IllegalArgumentException("A Name must not contain '/' or consist of whitespace, except that it may be a zero-length string.")
    }

    override fun equals(other: Any?): Boolean =
        other != null && other is Name && other.value == value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value
}