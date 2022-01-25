package com.prosumma.kontainer

interface Store {
    operator fun get(key: Key): String?
    operator fun set(key: Key, json: String)
}