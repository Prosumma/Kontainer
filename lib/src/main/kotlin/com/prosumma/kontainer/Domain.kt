package com.prosumma.kontainer

/**
 * The base class of all domains.
 *
 * Don't inherit from this type. Instead, inherit from
 * [ChildDomain], because this type is orphaned: it can
 * neither have a parent nor children.
 *
 * The properties of `Domain` begin with the prefix
 * `ko` so that users can add words such as `name`,
 * `container`, `key`, etc. for their own use.
 */
abstract class Domain internal constructor(val koname: Name) {
    abstract val kocontainer: Container
    open val kokey: Key
        get() = Key.create(koname)
}