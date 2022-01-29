package com.prosumma.kontainer

/**
 * A `ParentDomain` is a [Domain] which may have zero or more child domains
 * ([ChildDomain]).
 *
 * You want to inherit from [ChildDomain], not this one.
 */
abstract class ParentDomain(name: Name): Domain(name) {
    private val kochildren: MutableMap<Name, ChildDomain<*>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    operator fun <P: ParentDomain, C: ChildDomain<P>> get(name: Name, create: (Name, P) -> C): C =
        kochildren.getOrPut(name) {
            val child = create(name, this as P)
            if (child.koname != name)
                throw IllegalArgumentException("Names do not match.")
            if (child.koparent != this)
                throw IllegalArgumentException("Parents do not match.")
            child
        } as C

    @Suppress("UNCHECKED_CAST")
    operator fun <P: ParentDomain, C: ChildDomain<P>> get(name: Name): C? =
        kochildren[name]?.let { it as C }
}