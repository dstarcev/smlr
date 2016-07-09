package com.dstarcev.smlr.service

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DefaultKeyMapperServiceTest {
    @InjectMocks
    val service: KeyMapperService = DefaultKeyMapperService()

    private val KEY = "aAbBcCdD"

    private val LINK_A = "http://www.google.com"
    private val LINK_B = "http://www.yahoo.com"

    @Mock
    lateinit var converter: KeyConverterService

    private val ID_A = 10000000L
    private val ID_B = 10000001L

    private val KEY_A = "abc"
    private val KEY_B = "cde"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(converter.keyToId(KEY_A))
                .thenReturn(ID_A)
        Mockito.`when`(converter.idToKey(ID_A))
                .thenReturn(KEY_A)
        Mockito.`when`(converter.keyToId(KEY_B))
                .thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_B))
                .thenReturn(KEY_B)
    }

    @Test
    fun clientCanAddLinks() {
        val keyA = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))
        val keyB = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))
        assertNotEquals(keyA, keyB)
    }

    @Test
    fun clientCannotGetLinkIfKeyIsNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }
}