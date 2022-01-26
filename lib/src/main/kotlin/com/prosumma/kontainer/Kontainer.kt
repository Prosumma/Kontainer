package com.prosumma.kontainer

class Kontainer(override val container: Container): ParentDomain(Name("")) {
    constructor(store: Store): this(StandardContainer(store))
}