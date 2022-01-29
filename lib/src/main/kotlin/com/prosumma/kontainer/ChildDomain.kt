package com.prosumma.kontainer

/**
 * A `ChildDomain` is a [Domain] which may have
 * a parent. Every `ChildDomain` is also a [ParentDomain],
 * meaning that it may appear at any point in the domain
 * hierarchy except at the root.
 *
 * All custom domains derive from this class. The pattern
 * is as follows:
 *
 * ```kotlin
 * // This one is a child of the root.
 * class MyDomain(parent: Kontainer): ChildDomain<Kontainer>("my", parent)
 * val Kontainer.my: MyDomain by child(::MyDomain)
 *
 * // This one is a child of MyDomain and thus a grandchild of the root.
 * class YourDomain(parent: MyDomain): ChildDomain<MyDomain>("your", parent) {
 *   val cost: Int? by contained()
 * }
 * val MyDomain.your: YourDomain by child(::YourDomain)
 *
 * // This one has no inherent name or parent and is thus reusable.
 * class ReusableDomain<P: ParentDomain>(name: Name, parent: P): ChildDomain(name, parent)
 * val MyDomain.reusable: ReusableDomain<MyDomain> by child(::ReusableDomain)
 * ```
 *
 * Use the [child] property delegate to assign a domain to a parent through
 * a property. Given the [Kontainer], we can then access the domain and its
 * properties using ordinary dot syntax:
 *
 * ```kotlin
 * val kontainer = Kontainer(SharedPreferencesStore())
 * // Stores the value 100 to the key `/my/your/cost` in the underlying store.
 * kontainer.my.your.cost = 100
 * ```
 */
abstract class ChildDomain<Parent: ParentDomain>(
    name: Name, val koparent: Parent
): ParentDomain(name) {
    constructor(name: String, parent: Parent): this(Name(name), parent)

    final override val kocontainer: Container
        get() = koparent.kocontainer

    final override val kokey: Key
        get() = koparent.kokey + koname
}