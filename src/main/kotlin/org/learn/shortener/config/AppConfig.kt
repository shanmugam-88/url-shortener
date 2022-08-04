package org.learn.shortener.config

import org.apache.commons.validator.routines.UrlValidator
import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Value("\${com.dkb.shortener.salt}")
    val mySalt : String = ""

    @Bean
    fun hashids() : Hashids {
        return Hashids(mySalt)
    }

    @Bean
    fun urlValidator() : UrlValidator {
        return UrlValidator()
    }
}