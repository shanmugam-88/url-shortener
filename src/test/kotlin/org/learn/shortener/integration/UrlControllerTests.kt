package org.learn.shortener.integration

import org.learn.shortener.dto.UrlRequest
import org.learn.shortener.exception.ApiError
import org.learn.shortener.util.ErrorMessage
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.hashids.Hashids
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlControllerTests(@Autowired val restTemplate: TestRestTemplate,
                         @Autowired val hashids: Hashids) {

    val url : String = "https://www.factory.com"

    @Test
    fun `Assert create shortener url, content and status code`() {
        val urlRequest = UrlRequest(url)
        val entity = restTemplate.postForEntity("/shortener", urlRequest, String::class.java)
        assertNotNull(entity)
        assertEquals(HttpStatus.CREATED, entity.statusCode)
        assertNotNull(entity.body)
    }

    @Test
    fun `Assert create shortener url, 400 status code`() {
        val urlRequest = UrlRequest("www.factory.com")
        val entity = restTemplate.postForEntity("/shortener", urlRequest, String::class.java)
        assertNotNull(entity)
        assertEquals(entity.statusCode, HttpStatus.BAD_REQUEST)
        val mapper = ObjectMapper().registerModule(KotlinModule())
        val apiError = mapper.readValue(entity.body.toString(), ApiError::class.java)
        assertNotNull(apiError)
        assertEquals(ErrorMessage.invalidUrl, apiError.message)
    }

    @Test
    fun `Assert get full url, content and status code`() {
        val urlRequest = UrlRequest(url)
        val entity = restTemplate.postForEntity("/shortener", urlRequest, String::class.java)
        assertNotNull(entity)
        assertEquals(HttpStatus.CREATED, entity.statusCode)
        assertNotNull(entity.body)

        val getEntity = restTemplate.getForEntity("/shortener/"+entity.body, String::class.java)
        assertNotNull(getEntity)
        assertEquals(HttpStatus.FOUND, getEntity.statusCode)
        assertNotNull(getEntity.body)
        assertEquals(url, getEntity.body)
    }

    @Test
    fun `Assert get full url invalidHash, content and 404 status code`() {
        val getEntity = restTemplate.getForEntity("/shortener/123", String::class.java)
        assertNotNull(getEntity)
        assertEquals(HttpStatus.NOT_FOUND, getEntity.statusCode)

    }

    @Test
    fun `Assert get full url validHash, content and 404 status code`() {
        val getEntity = restTemplate.getForEntity("/shortener/"+hashids.encode(34), String::class.java)
        assertNotNull(getEntity)
        assertEquals(HttpStatus.NOT_FOUND, getEntity.statusCode)

    }
}