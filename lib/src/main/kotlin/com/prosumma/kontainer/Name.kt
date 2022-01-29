package com.prosumma.kontainer

import kotlin.text.Regex
import kotlin.text.RegexOption

/**
 * A `Name` represents a hierarchical component of a [Key].
 * For example, in the key `/config/metrics/width`, `config`,
 * `metrics` and `width` are all `Name`s.
 *
 * The main purpose of this type is to validate a `Name` and
 * make sure it follows the naming rules:
 *
 * A name may consist of:
 *
 * - an empty string.
 * - any sequence of characters excluding `/` and whitespace.
 */
class Name(private val value: String) {
    companion object {
        private val regex = Regex("""^[^/\s]*$""", RegexOption.IGNORE_CASE)

        fun validate(value: String): Boolean =
            regex.matches(value)
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