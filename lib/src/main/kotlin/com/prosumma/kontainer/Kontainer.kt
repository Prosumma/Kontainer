package com.prosumma.kontainer

/**
 * `Kontainer` is the root [Domain].
 */
class Kontainer(override val kocontainer: Container): ParentDomain(Name("")) {
    constructor(store: Store): this(StandardContainer(store))
}