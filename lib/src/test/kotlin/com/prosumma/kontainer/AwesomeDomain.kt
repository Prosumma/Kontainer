package com.prosumma.kontainer

class AwesomeDomain(parent: Kontainer): ChildDomain<Kontainer>("awesome", parent) {
    var watusi: Int? by contained()
}

class CrazyDomain<P: ParentDomain>(name: Name, parent: P): ChildDomain<P>(name, parent)

val Kontainer.awesome: AwesomeDomain by child(::AwesomeDomain)
val Kontainer.crazy: CrazyDomain<Kontainer> by child(::CrazyDomain)