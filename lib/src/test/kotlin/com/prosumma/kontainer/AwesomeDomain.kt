package com.prosumma.kontainer

class AwesomeDomain(parent: RootDomain): ChildDomain<RootDomain>("awesome", parent) {
    var watusi: Int? by contained()
}

val RootDomain.awesome: AwesomeDomain by child(::AwesomeDomain)