package com.demospring.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


////http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
open class SwaggerConfiguration {
    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.demospring"))
            .paths(regex("/.*"))
//            .apis(RequestHandlerSelectors.basePackage("aurum.spring_server.security"))
//            .paths(regex("/login.*"))

            .build()
            .apiInfo(metaInfo())
//            .securityContexts(listOf(securityContext()))
//            .securitySchemes(listOf(apiKey()))

    private fun metaInfo(): ApiInfo {

        return ApiInfo(
                "API",
                "API Description",
                "1.0",
                "Terms of Service",
                Contact("Demo Tech Development", "www.demotech.com", "email@mail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html",
                ArrayList()
        )
    }
}