package com.prosumma.kontainer

class RootDomain(override val container: Container): ParentDomain(Name("")) {
    constructor(store: Store): this(StandardContainer(store))
}