package com.dstarcev.smlr.service

import org.springframework.stereotype.Component

@Component
class DefaultKeyConverterService : KeyConverterService {
    private val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val size = alphabet.length

    override fun idToKey(id: Long): String =
            if (id == 0L) ""
            else idToKey(id / size) +
                    alphabet[(id % size).toInt()].toString()

    override fun keyToId(key: String) =
            key.map { alphabet.indexOf(it).toLong() }
                    .reduce { acc, num -> acc * size + num }
}