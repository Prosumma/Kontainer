package com.prosumma.kontainer

class AwesomeDomain(parent: Kontainer): ChildDomain<Kontainer>("awesome", parent) {
    var watusi: Int? by kontained()
}

class CrazyDomain<P: ParentDomain>(name: Name, parent: P): ChildDomain<P>(name, parent) {
    var person: Person? by kontained()
}

val Kontainer.awesome: AwesomeDomain by child(::AwesomeDomain)
val AwesomeDomain.crazy: CrazyDomain<AwesomeDomain> by child(::CrazyDomain)