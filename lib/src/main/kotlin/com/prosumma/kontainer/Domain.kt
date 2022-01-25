package com.prosumma.kontainer

abstract class Domain internal constructor(val name: Name) {
    abstract val container: Container
    open val key: Key
        get() = Key.create(name)
}