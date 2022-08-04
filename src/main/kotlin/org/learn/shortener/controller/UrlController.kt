package org.learn.shortener.controller

import org.learn.shortener.dto.UrlRequest
import org.learn.shortener.service.UrlService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI
import java.util.*
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/shortener")
class UrlController(private var urlService: UrlService) {

    /**
     * API to get a full URL.
     *
     * @param hashValue String.
     * @return String
     */
    @ApiOperation(value = "API to get a Full URL.")
    @ApiResponses(
        ApiResponse(code = 302, message = "Found"),
        ApiResponse(code = 400, message = "Bad Request"),
        ApiResponse(code = 404, message = "Not Found")
    )
    @GetMapping("/{hashValue}")
    fun getFullUrl(@PathVariable hashValue : @NotNull String) : ResponseEntity<String> {
        val urlOptional : Optional<String> = urlService.getFullUrl(hashValue)
        if (urlOptional.isEmpty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlOptional.get())).body(urlOptional.get())
    }

    /**
     * API to get shortener URL.
     *
     * @param urlRequest UrlRequest.
     * @return String
     */
    @ApiOperation(value = "API to get shortener URL")
    @ApiResponses(
        ApiResponse(code = 201, message = "Created"),
        ApiResponse(code = 400, message = "Bad Request")
    )
    @PostMapping
    fun getShortUrl(@RequestBody urlRequest: UrlRequest) : ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlService.getShortUrl(urlRequest))
    }

}