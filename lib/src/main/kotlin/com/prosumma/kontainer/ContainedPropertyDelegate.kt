package com.prosumma.kontainer

import kotlin.reflect.KProperty

typealias Getter<T> = (Key, Container) -> T
typealias Setter<T> = (Key, Container, T) -> Unit

class ContainedPropertyDelegate<D: Domain, T> @PublishedApi internal constructor (
    private val name: Name?,
    private val getter: Getter<T>,
    private val setter: Setter<T>
) {
    operator fun getValue(domain: D, property: KProperty<*>): T =
        getter(domain.kokey + (name ?: Name(property.name)), domain.kocontainer)

    operator fun setValue(domain: D, property: KProperty<*>, value: T) =
        setter(domain.kokey + (name ?: Name(property.name)), domain.kocontainer, value)
}

inline fun <D: Domain, reified T> contained(
    name: Name?,
    noinline default: () -> T
): ContainedPropertyDelegate<D, T> {
    val getter: Getter<T> = { key, container ->
        container[key, default]
    }
    val setter: Setter<T> = { key, container, value ->
        container[key] = value
    }
    return ContainedPropertyDelegate(name, getter, setter)
}

inline fun <D: Domain, reified T> contained(
    name: String,
    noinline default: () -> T
): ContainedPropertyDelegate<D, T> = contained(Name(name), default)

inline fun <D: Domain, reified T> contained(
    noinline default: () -> T
): ContainedPropertyDelegate<D, T> = contained(null, default)

inline fun <D: Domain, reified T> contained(name: Name? = null): ContainedPropertyDelegate<D, T?> =
    contained(name) { null }

inline fun <D: Domain, reified T> contained(name: String): ContainedPropertyDelegate<D, T?> =
    contained(name) { null }