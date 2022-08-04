package org.learn.shortener.unit

import org.learn.shortener.controller.UrlController
import org.learn.shortener.dto.UrlRequest
import org.learn.shortener.model.Url
import org.learn.shortener.repository.UrlRepository
import org.learn.shortener.service.UrlServiceImpl
import org.apache.commons.validator.routines.UrlValidator
import org.hashids.Hashids
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.util.*

@ExtendWith(MockitoExtension::class)
class UrlControllerTests {

    private var urlController: UrlController? = null

    @Mock
    private lateinit var urlRepository: UrlRepository

    private val hashids = Hashids("Test")

    private val urlTest : String = "https://www.factory.com"

    @BeforeEach
    fun init() {
        val urlService = UrlServiceImpl(urlRepository, hashids, UrlValidator())
        urlController = UrlController(urlService)
    }

    @Test
    fun `Assert create shortener url, content and status code`() {
        Mockito.`when`(urlRepository.save(Mockito.any(Url::class.java))).thenReturn(getUrlModel())
        val urlRequest = UrlRequest(urlTest)
        val responseEntity = urlController?.getShortUrl(urlRequest)
        Assertions.assertNotNull(responseEntity)
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity?.statusCode)
        Assertions.assertNotNull(responseEntity?.body)
    }

    @Test
    fun `Assert get full url, content and status code`() {
        Mockito.`when`(urlRepository.findById(Mockito.any())).thenReturn(Optional.of(getUrlModel()))
        val responseEntity = urlController?.getFullUrl(hashids.encode(12))
        Assertions.assertNotNull(responseEntity)
        Assertions.assertEquals(HttpStatus.FOUND, responseEntity?.statusCode)
        Assertions.assertEquals(getUrlModel().url, responseEntity?.body)
    }

    private fun getUrlModel(): Url {
        val url = Url()
        url.url = urlTest
        url.id = 1
        return url
    }
}