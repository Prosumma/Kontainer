package com.prosumma.kontainer

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DomainTests {
    @Test
    fun testDomains() {
        val store = MemoryStore()
        val root = RootDomain(store)
        assertNull(root.awesome.watusi)
        root.awesome.watusi = 3
        assertEquals(root.awesome.watusi, 3)
        assertEquals(store[root.awesome.key + "watusi"], "3")
    }

    @Test
    fun testCachingContainer() {
        val store = MemoryStore()
        val container = CachingContainer(store)
        val root = RootDomain(container)
        assertNull(root.awesome.watusi)
        root.awesome.watusi = 3
        assertEquals(root.awesome.watusi, 3)
        assertEquals(store[root.awesome.key + "watusi"], "3")
        assertEquals(container[root.awesome.key + "watusi"], 3)
    }
}