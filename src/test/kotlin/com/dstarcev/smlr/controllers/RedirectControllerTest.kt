package com.dstarcev.smlr.controllers

import com.dstarcev.smlr.SmlrApplication
import com.dstarcev.smlr.service.KeyMapperService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf(SmlrApplication::class))
@WebAppConfiguration
class RedirectControllerTest {
    @Autowired
    lateinit var webApplicationContext: WebApplicationContext
    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: RedirectController

    private val KEY = "JJKHg3fs"
    private val BAD_KEY = "olololo"
    private val LINK = "http://ya.ru"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = webAppContextSetup(webApplicationContext).build()

        Mockito.`when`(service.getLink(KEY))
                .thenReturn(KeyMapperService.Get.Link(LINK))

        Mockito.`when`(service.getLink(BAD_KEY))
                .thenReturn(KeyMapperService.Get.NotFound(BAD_KEY))
    }

    @Test
    fun controllerMustRedirectUsWhenRequestIsSuccessful() {
        mockMvc.perform(get("/$KEY"))
                .andExpect(status().`is`(302))
                .andExpect(header().string("Location", LINK))
    }

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_KEY"))
                .andExpect(status().`is`(404))
    }
}