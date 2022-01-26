package com.prosumma.kontainer

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DomainTests {
    @Test
    fun testDomains() {
        val store = MemoryStore()
        val kontainer = Kontainer(store)
        assertNull(kontainer.awesome.watusi)
        kontainer.awesome.watusi = 3
        assertEquals(kontainer.awesome.watusi, 3)
        assertEquals(store[kontainer.awesome.key + "watusi"], "3")
    }

    @Test
    fun testCachingContainer() {
        val store = MemoryStore()
        val container = CachingContainer(store)
        val kontainer = Kontainer(container)
        assertNull(kontainer.awesome.watusi)
        kontainer.awesome.watusi = 3
        assertEquals(kontainer.awesome.watusi, 3)
        assertEquals(store[kontainer.awesome.key + "watusi"], "3")
        assertEquals(container[kontainer.awesome.key + "watusi"], 3)
    }
}