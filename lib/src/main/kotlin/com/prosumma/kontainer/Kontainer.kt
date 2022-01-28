package com.prosumma.kontainer

/**
 * `Kontainer` is the root [Domain].
 */
class Kontainer(override val container: Container): ParentDomain(Name("")) {
    constructor(store: Store): this(StandardContainer(store))
}