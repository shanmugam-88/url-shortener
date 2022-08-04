package org.learn.shortener.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/**
 * Swagger Configuration class.
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {
    /**
     * Swagger configuration bean.
     *
     * @return Docket.
     */
    @Bean
    fun swaggerConfiguration(): Docket? {
        return Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("org.learn.shortener.controller"))
                .build().apiInfo(apiDetails())
    }

    private fun apiDetails(): ApiInfo {
        return ApiInfo("Shortener Application", "Shortener application", "1.0.0", "",
                Contact("DKB Codefactory", "https://www.dkbcodefactory.com/", "https://www.dkbcodefactory.com/"), "free", "", emptyList())
    }
}