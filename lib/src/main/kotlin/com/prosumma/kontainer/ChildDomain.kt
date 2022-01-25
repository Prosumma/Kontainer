package com.prosumma.kontainer

abstract class ChildDomain<Parent: ParentDomain>(name: Name, val parent: Parent): ParentDomain(name) {
    constructor(name: String, parent: Parent): this(Name(name), parent)

    final override val container: Container
        get() = parent.container

    final override val key: Key
        get() = parent.key + name
}