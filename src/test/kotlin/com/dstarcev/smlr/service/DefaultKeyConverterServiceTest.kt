package com.dstarcev.smlr.service

import org.junit.Assert
import org.junit.Test
import java.util.*

class DefaultKeyConverterServiceTest {
    private val service = DefaultKeyConverterService()

    @Test
    fun givenIdMustBeConvertibleBothWays() {
        val rand = Random()
        for (i in 0..1000) {
            val initialId = Math.abs(rand.nextLong())
            val key = service.idToKey(initialId)
            val id = service.keyToId(key)
            Assert.assertEquals(initialId, id)
        }
    }
}