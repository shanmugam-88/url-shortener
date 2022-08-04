package org.learn.shortener.service

import org.learn.shortener.dto.UrlRequest
import java.util.Optional

interface UrlService {

    /**
     * API to get shortener URL.
     *
     * @param urlRequest UrlRequest.
     * @return String
     */
    fun getShortUrl(urlRequest: UrlRequest) : String
    /**
     * API to get a full URL.
     *
     * @param hash String.
     * @return String
    */
    fun getFullUrl(hash: String) : Optional<String>
}