package com.prosumma.kontainer

abstract class ParentDomain(name: Name): Domain(name) {
    private val children: MutableMap<Name, ChildDomain<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    operator fun <P: ParentDomain, C: ChildDomain<P>> get(name: Name, create: (Name, P) -> C): C =
        children.getOrPut(name) {
            val child = create(name, this as P)
            if (child.name != name)
                throw IllegalArgumentException("Names do not match.")
            if (child.parent != this)
                throw IllegalArgumentException("Parents do not match.")
            child
        } as C
}