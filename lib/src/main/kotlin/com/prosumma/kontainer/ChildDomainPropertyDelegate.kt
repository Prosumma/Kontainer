package com.prosumma.kontainer

import kotlin.reflect.KProperty

class ChildDomainPropertyDelegate<P: ParentDomain, C: ChildDomain<P>> internal constructor(
    private val name: Name?,
    private val create: (Name, P) -> C
) {
    operator fun getValue(parent: P, property: KProperty<*>): C =
        parent[this.name ?: Name(property.name), create]
}

fun <P: ParentDomain, C: ChildDomain<P>> child(name: Name?, create: (Name, P) -> C):
        ChildDomainPropertyDelegate<P, C> = ChildDomainPropertyDelegate(name, create)

fun <P: ParentDomain, C: ChildDomain<P>> child(name: String, create: (Name, P) -> C):
        ChildDomainPropertyDelegate<P, C> = ChildDomainPropertyDelegate(Name(name), create)

fun <P: ParentDomain, C: ChildDomain<P>> child(create: (Name, P) -> C):
        ChildDomainPropertyDelegate<P, C> = ChildDomainPropertyDelegate(null, create)

private fun <A, B, C> expand(f: (B) -> C): (A, B) -> C = { _, b ->  f(b) }

fun <P: ParentDomain, C: ChildDomain<P>> child(name: Name?, create: (P) -> C):
        ChildDomainPropertyDelegate<P, C> = ChildDomainPropertyDelegate(name, expand(create))

fun <P: ParentDomain, C: ChildDomain<P>> child(name: String, create: (P) -> C):
        ChildDomainPropertyDelegate<P, C> = ChildDomainPropertyDelegate(Name(name), expand(create))

fun <P: ParentDomain, C: ChildDomain<P>> child(create: (P) -> C):
        ChildDomainPropertyDelegate<P, C> = ChildDomainPropertyDelegate(null, expand(create))