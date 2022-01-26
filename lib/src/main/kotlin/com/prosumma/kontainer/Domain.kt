package com.prosumma.kontainer

/**
 * The base class of all domains.
 *
 * Don't inherit from this type. Instead, inherit from
 * [ChildDomain], because this type is orphaned: it can
 * neither have a parent nor children.
 */
abstract class Domain internal constructor(val name: Name) {
    abstract val container: Container
    open val key: Key
        get() = Key.create(name)
}